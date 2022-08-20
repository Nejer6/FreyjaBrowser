package com.nejer.freyja.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Branch(
    val value: String = "",
    val children: MutableList<Branch> = mutableListOf()
)

@Entity(tableName = "folders_table")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val parentId: Int,
    @ColumnInfo
    val name: String = ""
)
