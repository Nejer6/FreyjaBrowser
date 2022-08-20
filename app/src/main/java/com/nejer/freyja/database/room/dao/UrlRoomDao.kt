package com.nejer.freyja.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nejer.freyja.models.Url

@Dao
interface UrlRoomDao {

    @Query("SELECT * FROM urls_table")
    fun getAllUrls(): LiveData<List<Url>>

    @Insert
    suspend fun addUrl(url: Url)

    @Update
    suspend fun updateUrl(url: Url)

    @Delete
    suspend fun deleteUrl(url: Url)

    @Query("SELECT EXISTS (SELECT 1 FROM urls_table WHERE url = :url)")
    fun existsUrl(url: String): LiveData<Boolean>

    @Query("DELETE FROM urls_table WHERE url = :url")
    suspend fun deleteFolderByName(url: String)
}