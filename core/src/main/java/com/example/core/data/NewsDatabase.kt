package com.example.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        private val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        private val factory = SupportFactory(passphrase)

        @Volatile
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "News.db"
                )
                    .openHelperFactory(factory)
                    .build().also { instance = it }
            }
    }
}
