package com.example.shared.presentation

import app.cash.turbine.test
import com.example.core.domain.Result
import com.example.core.domain.model.News
import com.example.core.domain.usecase.NewsUseCase
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private val newsUseCase: NewsUseCase = mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NewsViewModel(newsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchHeadlineNews should update headlineNews with success data`() = runTest {

        val mockNewsList = listOf(
            News(
                id = "1",
                name = "Headline News",
                description = "Description",
                summary = "Summary",
                mediaCover = "cover.jpg",
                imageLogo = "logo.jpg",
                link = "http://example.com",
                ownerName = "Owner",
                cityName = "City",
                quota = 10,
                beginTime = "2025-01-01",
                endTime = "2025-01-02",
                category = "General",
                isBookmarked = false
            )
        )

        every { newsUseCase.getHeadlineNews() } returns flow {
            emit(Result.Success(mockNewsList))
        }

        viewModel.headlineNews.test {
            assertEquals(Result.Loading, awaitItem())
            viewModel.fetchHeadlineNews()
            assertEquals(Result.Success(mockNewsList), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `fetchBookmarkedNews should update bookmarkedNews with success data`() = runTest {
        val mockBookmarkedNews = listOf(
            News(
                id = "2",
                name = "Bookmarked News",
                description = "Bookmarked Description",
                summary = "Summary",
                mediaCover = "cover2.jpg",
                imageLogo = "logo2.jpg",
                link = "http://example2.com",
                ownerName = "Owner2",
                cityName = "City2",
                quota = 5,
                beginTime = "2025-02-01",
                endTime = "2025-02-02",
                category = "Tech",
                isBookmarked = true
            )
        )

        every { newsUseCase.getBookmarkedNews() } returns flow {
            emit(mockBookmarkedNews)
        }

        viewModel.bookmarkedNews.test {
            assertEquals(emptyList<News>(), awaitItem())
            viewModel.fetchBookmarkedNews()             
            assertEquals(mockBookmarkedNews, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `saveNews should call useCase to save bookmarked news`() = runTest {
        val mockNews = News(
            id = "3",
            name = "Save News",
            description = "Save Description",
            summary = "Save Summary",
            mediaCover = "cover3.jpg",
            imageLogo = "logo3.jpg",
            link = "http://example3.com",
            ownerName = "Owner3",
            cityName = "City3",
            quota = 15,
            beginTime = "2025-03-01",
            endTime = "2025-03-02",
            category = "Health",
            isBookmarked = false
        )

        viewModel.saveNews(mockNews)

        coVerify { newsUseCase.setBookmarkedNews(mockNews, true) }
    }

    @Test
    fun `deleteNews should call useCase to delete bookmarked news`() = runTest {
        val mockNews = News(
            id = "4",
            name = "Delete News",
            description = "Delete Description",
            summary = "Delete Summary",
            mediaCover = "cover4.jpg",
            imageLogo = "logo4.jpg",
            link = "http://example4.com",
            ownerName = "Owner4",
            cityName = "City4",
            quota = 8,
            beginTime = "2025-04-01",
            endTime = "2025-04-02",
            category = "Sports",
            isBookmarked = true
        )

        viewModel.deleteNews(mockNews)

        coVerify { newsUseCase.setBookmarkedNews(mockNews, false) }
    }
}
