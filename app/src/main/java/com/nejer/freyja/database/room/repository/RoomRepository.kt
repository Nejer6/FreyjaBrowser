package com.nejer.freyja.database.room.repository

import androidx.lifecycle.LiveData
import com.nejer.freyja.database.DatabaseRepository
import com.nejer.freyja.database.room.dao.FolderRoomDao
import com.nejer.freyja.models.Folder

class RoomRepository(private val folderRoomDao: FolderRoomDao) : DatabaseRepository {
    override val readAll: LiveData<List<Folder>>
        get() = folderRoomDao.getAllFolders()

    override suspend fun create(folder: Folder, onSuccess: () -> Unit) {
        folderRoomDao.addFolder(folder = folder)
        onSuccess()
    }

    override suspend fun update(folder: Folder, onSuccess: () -> Unit) {
        folderRoomDao.updateFolder(folder = folder)
        onSuccess()
    }

    override suspend fun delete(folder: Folder, onSuccess: () -> Unit) {
        folderRoomDao.deleteFolder(folder = folder)
        onSuccess()
    }

    override fun exists(name: String): LiveData<Boolean> {
        return folderRoomDao.existsFolder(name = name)
    }

    override suspend fun deleteByName(name: String, onSuccess: () -> Unit) {
        folderRoomDao.deleteFolderByName(name = name)
        onSuccess()
    }
}