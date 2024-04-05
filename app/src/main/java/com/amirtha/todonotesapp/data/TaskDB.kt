package com.amirtha.roomdbamirtha

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Task::class],
    version = 4
)
abstract class TaskDB : RoomDatabase() {

    abstract val dao: TaskDao

}