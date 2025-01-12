package com.example.core.data


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
class NewsEntity(
    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "summary")
    val summary: String,

    @field:ColumnInfo(name = "penyelenggara")
    val penyelenggaraAcara :String,

    @field:ColumnInfo(name = "time")
    val waktu: String,

    @field:ColumnInfo(name = "quota")
    val quota : Int,

    @field:ColumnInfo(name = "link")
    val link :String,

    @field:ColumnInfo(name = "deskripsi")
    val desckripsi : String,

    @field:ColumnInfo(name = "registrasi")
    val registrasi : Int,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
) : Parcelable {
    override fun toString(): String {
        return "NewsEntity(title='$title', description='$desckripsi', isBookmarked=$isBookmarked)"
    }
}