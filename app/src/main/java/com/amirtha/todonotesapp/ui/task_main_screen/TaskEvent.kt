package com.amirtha.todonotesapp.ui.task_main_screen

import com.amirtha.roomdbamirtha.Task


// User Action
sealed interface TaskEvent {
    object SaveTask : TaskEvent
    data class SetTaskTopic(val topic: String) : TaskEvent
    data class SetTaskDesc(val desc: String) : TaskEvent
    data class SetTaskCompletion(val done: Boolean) : TaskEvent
    data class OnDoneChange(val todo: Task, val isDone: Boolean): TaskEvent

    object ShowDialog : TaskEvent
    object HideDialog : TaskEvent

    //    data class TaskCompletion(val )
    data class DeleteTask(val task: Task) : TaskEvent
}