package com.amirtha.todonotesapp.ui.task_main_screen

import com.amirtha.roomdbamirtha.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
//    val taskTopic: String = System.currentTimeMillis().toString(),
//    val taskDescription: String = System.currentTimeMillis().toString(),
    val taskTopic: String = "",
    val taskDescription: String = "",
    val taskCompletion: Boolean = false,
    val isAddingTask: Boolean = false,

    )