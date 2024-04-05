package com.amirtha.todonotesapp.ui.task_main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirtha.roomdbamirtha.Task
import com.amirtha.roomdbamirtha.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    private val _task = dao.getTask()
    private val _state = MutableStateFlow(TaskState())
    val state = combine(_state, _task) { state, tasks ->
        state.copy(
            tasks = tasks,

            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())


    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    dao.deleteTask(event.task)
                }
            }
            is TaskEvent.OnDoneChange -> {
                viewModelScope.launch {
                    viewModelScope.launch {
                        dao.upsertTask(
                            event.todo.copy(
                                isDone = event.isDone
                            )
                        )
                    }
//                    repository.insertTodo(
//                        event.todo.copy(
//                            isDone = event.isDone
//                        )
//                    )
                }
            }
            TaskEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingTask = false
                    )
                }

            }

            TaskEvent.SaveTask -> {

                val taskTopic = state.value.taskTopic
                val taskDesc = state.value.taskDescription
                val taskCompletion = state.value.taskCompletion

//                if (taskTopic.isBlank() || taskDesc.isBlank()) {
//                    return
//                }

                val task = Task(
                    taskTopic,
                    taskDesc,
                    taskCompletion
                )
                viewModelScope.launch {
                    dao.upsertTask(task)
                }
                _state.update {
                    it.copy(
                        isAddingTask = false,
//                        taskTopic = "",
                        taskTopic = state.value.taskTopic,
//                        taskDescription = "",
                        taskDescription =state.value.taskDescription,
//                        taskCompletion = false
                        taskCompletion = state.value.taskCompletion
                    )
                }
            }

            is TaskEvent.SetTaskCompletion -> {
                _state.update {
                    it.copy(
                        taskCompletion = event.done
                    )
                }
            }

            is TaskEvent.SetTaskDesc -> {
                _state.update {
                    it.copy(
                        taskDescription = event.desc
                    )
                }
            }

            is TaskEvent.SetTaskTopic -> {
                _state.update {
                    it.copy(
                        taskTopic = event.topic
                    )
                }
            }

            TaskEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingTask = true
                    )
                }
            }
        }
    }
}