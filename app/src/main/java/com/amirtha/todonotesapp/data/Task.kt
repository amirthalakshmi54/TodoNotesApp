package com.amirtha.roomdbamirtha

import androidx.room.Entity
import androidx.room.PrimaryKey

//Default class name as Table name
@Entity
data class Task(
//    @PrimaryKey
    val topic: String,
    val description: String,
    var isDone: Boolean = false,
    @PrimaryKey val id: Int? = null

//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
)
