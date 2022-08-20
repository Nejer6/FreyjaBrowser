package com.nejer.freyja.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Branch(
    val value: String = "",
    val children: MutableList<Branch> = mutableListOf()
)

@Entity(tableName = "urls_table")
data class Url(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val url: String = ""
)

data class Folder(
    val value: String,
    val children: MutableList<Folder>
)
