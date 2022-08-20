package com.nejer.freyja.database.room.repository

import androidx.lifecycle.LiveData
import com.nejer.freyja.database.DatabaseRepository
import com.nejer.freyja.database.room.dao.UrlRoomDao
import com.nejer.freyja.models.Url

class RoomRepository(private val urlRoomDao: UrlRoomDao) : DatabaseRepository {
    override val readAll: LiveData<List<Url>>
        get() = urlRoomDao.getAllUrls()

    override suspend fun create(url: Url, onSuccess: () -> Unit) {
        urlRoomDao.addUrl(url = url)
        onSuccess()
    }

    override suspend fun update(url: Url, onSuccess: () -> Unit) {
        urlRoomDao.updateUrl(url = url)
        onSuccess()
    }

    override suspend fun delete(url: Url, onSuccess: () -> Unit) {
        urlRoomDao.deleteUrl(url = url)
        onSuccess()
    }

    override fun exists(url: String): LiveData<Boolean> {
        return urlRoomDao.existsUrl(url = url)
    }

    override suspend fun deleteByName(url: String, onSuccess: () -> Unit) {
        urlRoomDao.deleteFolderByName(url = url)
        onSuccess()
    }
}