package com.amirtha.todonotesapp.ui.task_main_screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit
) {

    val textToSend = buildString {
        state.tasks.forEach { task ->
//            append("Task(topic=${task.topic}, description=${task.description}, isDone=${task.isDone}, id=${task.id})\n")
            append(" ${task.topic},\n ${task.description},\n isDone: ${task.isDone}\n")
        }
    }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToSend)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFF321466)),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Todo Notes",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 6.dp)
                        )

                        IconButton(onClick = {
                            context.startActivity(shareIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = " task",
                                tint = Color(0xFFF09B1F)
                            )
                        }
                    }

                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TaskEvent.SetTaskTopic(""))
                    onEvent(TaskEvent.SetTaskDesc(""))
                    onEvent(TaskEvent.ShowDialog)
                },
                containerColor = Color(0xFF321466)
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add task",
                    tint = Color(0xFFF09B1F), modifier = Modifier.size(35.dp)
                )
            }
        }
    ) { padding ->

        if (state.isAddingTask) {
            AddContactDialog(state, onEvent)
        }
        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.tasks) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(top=4.dp)
                            .clickable {
                                onEvent(TaskEvent.ShowDialog)
                                onEvent(TaskEvent.SetTaskTopic(task.topic))
                                onEvent(TaskEvent.SetTaskDesc(task.description))
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF09B1F)),
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = task.topic,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Checkbox(
                                    checked = task.isDone,
                                    onCheckedChange = { isChecked ->
                                        onEvent(TaskEvent.OnDoneChange(task, isChecked))
                                    },
                                    modifier = Modifier.padding(8.dp).size(26.dp),
                                    colors = CheckboxDefaults.colors(Color(0xFF321466))
                                )

                            }

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = task.description, fontSize = 20.sp,
                                    color = Color.White
                                    )
                                var deleteIconClicked by remember {
                                    mutableStateOf(false)
                                }
                                IconButton(onClick = {
                                    deleteIconClicked = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete task",
                                        tint=Color(0xFF321466),
                                        modifier = Modifier.padding(start=4.dp)
                                    )
                                }
                                if (deleteIconClicked) {
                                    DeleteAlertDialog(onDismiss = {
                                        deleteIconClicked = false
                                    }, onConfirm = {
                                        onEvent(TaskEvent.DeleteTask(task))
                                        deleteIconClicked = false
                                    })
                                }

                            }

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DeleteAlertDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("YES")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("NO")
            }
        },
        text = {
            Text(
                text = "Are you sure to Delete the Task",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        })

}