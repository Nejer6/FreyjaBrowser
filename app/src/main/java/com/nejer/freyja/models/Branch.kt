package com.nejer.freyja.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nejer.freyja.Constants

@Entity(tableName = Constants.Keys.URLS_TABLE)
data class Url(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val url: String = ""
)

data class Folder(
    val value: String = "",
    val children: MutableList<Folder> = mutableListOf(),
    val parent: Folder? = null
)
