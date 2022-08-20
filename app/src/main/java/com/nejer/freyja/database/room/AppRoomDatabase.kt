package com.nejer.freyja.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nejer.freyja.database.room.dao.UrlRoomDao
import com.nejer.freyja.models.Url

@Database(entities = [Url::class], version = 4)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): UrlRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "folders_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE as AppRoomDatabase
            } else INSTANCE as AppRoomDatabase
        }
    }
}