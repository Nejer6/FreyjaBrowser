package com.nejer.freyja.database

import androidx.lifecycle.LiveData
import com.nejer.freyja.models.Folder

interface DatabaseRepository {
    val readAll: LiveData<List<Folder>>

    suspend fun create(folder: Folder, onSuccess: () -> Unit)

    suspend fun update(folder: Folder, onSuccess: () -> Unit)

    suspend fun delete(folder: Folder, onSuccess: () -> Unit)

    fun exists(name: String): LiveData<Boolean>

    suspend fun deleteByName(name: String, onSuccess: () -> Unit)

}