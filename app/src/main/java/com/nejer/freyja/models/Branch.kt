package com.nejer.freyja.models

data class Branch(
    val value: String = "",
    val children: MutableList<Branch> = mutableListOf()
)
