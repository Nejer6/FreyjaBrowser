package com.nejer.freyja.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nejer.freyja.models.Folder

@Dao
interface FolderRoomDao {

    @Query("SELECT * FROM folders_table")
    fun getAllFolders(): LiveData<List<Folder>>

    @Insert
    suspend fun addFolder(folder: Folder)

    @Update
    suspend fun updateFolder(folder: Folder)

    @Delete()
    suspend fun deleteFolder(folder: Folder)

    @Query("SELECT EXISTS (SELECT 1 FROM folders_table WHERE name = :name)")
    fun existsFolder(name: String): LiveData<Boolean>

    @Query("DELETE FROM folders_table WHERE name = :name")
    suspend fun deleteFolderByName(name: String)
}