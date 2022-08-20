package com.nejer.freyja

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nejer.freyja.database.room.AppRoomDatabase
import com.nejer.freyja.database.room.repository.RoomRepository

class MainViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    fun initDatabase(onSuccess: () -> Unit) {
        val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
        REPOSITORY = RoomRepository(dao)
        onSuccess()
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}