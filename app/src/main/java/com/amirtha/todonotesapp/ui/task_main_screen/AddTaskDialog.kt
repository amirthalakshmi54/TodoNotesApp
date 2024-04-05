package com.amirtha.todonotesapp.ui.task_main_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AddContactDialog(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    modifier: Modifier = Modifier
) {


    AlertDialog(

        title = {
            Text(
                text = "Add Todo Notes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF321466)
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.taskTopic,
                    onValueChange = {
                        onEvent(TaskEvent.SetTaskTopic(it))
                    },
                    placeholder = {
                        Text(text = "Task Topic")
                    }
                )
                TextField(
                    value = state.taskDescription,
                    onValueChange = {
                        onEvent(TaskEvent.SetTaskDesc(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )

            }
        },
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    if (state.taskDescription.isNotEmpty() || state.taskTopic.isNotEmpty()) {
                        onEvent(TaskEvent.SaveTask)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFF09B1F))
            ) {
                Text(
                    text = "Save", color = Color(0xFF321466),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onEvent(TaskEvent.HideDialog)
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFF09B1F))
            ) {
                Text(
                    text = "Cancel", color = Color(0xFF321466),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    )
}