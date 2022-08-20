package com.nejer.freyja.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nejer.freyja.database.room.dao.FolderRoomDao
import com.nejer.freyja.models.Folder

@Database(entities = [Folder::class], version = 3)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): FolderRoomDao

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