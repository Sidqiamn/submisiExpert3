
package com.example.shared.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val summary: String,
    val mediaCover: String,
    val imageLogo: String,
    val link: String,
    val description: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val name: String,
    val id: String,
    val beginTime: String,
    val endTime: String,
    val category: String,
    var isBookmarked: Boolean
) : Parcelable