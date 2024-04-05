package com.amirtha.todonotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.amirtha.roomdbamirtha.TaskDB
import com.amirtha.todonotesapp.ui.task_main_screen.TaskScreen
import com.amirtha.todonotesapp.ui.task_main_screen.TaskViewModel
import com.amirtha.todonotesapp.ui.theme.TodoNotesAppTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TaskDB::class.java,
            "task.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private val viewModel by viewModels<TaskViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {9
                    return TaskViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoNotesAppTheme {
                val state by viewModel.state.collectAsState()
                TaskScreen(state =state, onEvent =viewModel::onEvent)
            }
        }
    }
}


