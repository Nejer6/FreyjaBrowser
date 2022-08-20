package com.nejer.freyja.database

import androidx.lifecycle.LiveData
import com.nejer.freyja.models.Url

interface DatabaseRepository {
    val readAll: LiveData<List<Url>>

    suspend fun create(url: Url, onSuccess: () -> Unit)

    suspend fun update(url: Url, onSuccess: () -> Unit)

    suspend fun delete(url: Url, onSuccess: () -> Unit)

    fun exists(url: String): LiveData<Boolean>

    suspend fun deleteByName(url: String, onSuccess: () -> Unit)

}