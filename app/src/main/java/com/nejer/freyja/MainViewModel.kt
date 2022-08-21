package com.nejer.freyja

import android.app.Application
import androidx.lifecycle.*
import com.nejer.freyja.database.room.AppRoomDatabase
import com.nejer.freyja.database.room.repository.RoomRepository
import com.nejer.freyja.models.Folder
import com.nejer.freyja.models.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    var mainFolder: Folder = Folder()

    fun initDatabase(onSuccess: () -> Unit) {
        val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
        REPOSITORY = RoomRepository(dao)
        onSuccess()
    }

    fun addFolder(url: Url, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(url = url) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun getAllUrls() = REPOSITORY.readAll

    fun existsFolder(url: String) = REPOSITORY.exists(url = url)

    fun deleteFolderByName(url: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteByName(url = url) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun changeFolder(urls: List<Url>) {
        val newMainFolder = Folder(value = "")
        urls.forEach { url ->
            var currentFolder = newMainFolder
            val partsOfUrl = url.url.split("/").toMutableList()
            if (partsOfUrl.last().isEmpty()) {
                partsOfUrl.removeLast()
            }

            loop@ for (part in partsOfUrl) {

                for (folder in currentFolder.children) {

                    if (folder.value == part) {
                        currentFolder = folder
                        continue@loop
                    }
                }
                val newFolder = Folder(value = part, parent = currentFolder)
                //Log.d("tag", newFolder.toString())
                currentFolder.children.add(newFolder)
                currentFolder = newFolder

            }
        }
        mainFolder = newMainFolder
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