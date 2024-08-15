package com.internprove_task_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StopwatchApp()
            }
        }
    }
}

@Composable
fun StopwatchApp() {
    var timeInMillis by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L)
            timeInMillis += 10L
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color.Cyan, Color.Blue)
            ))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = formatTime(timeInMillis),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StopwatchIconButton(
                    onClick = { isRunning = true },
                    icon = painterResource(id = R.drawable.play),
                    contentDescription = "Start"
                )
                StopwatchIconButton(
                    onClick = { isRunning = false },
                    icon = painterResource(id = R.drawable.stop),
                    contentDescription = "Pause"
                )
                StopwatchIconButton(
                    onClick = {
                        isRunning = false
                        timeInMillis = 0L
                    },
                    icon = painterResource(id = R.drawable.reset),
                    contentDescription = "Reset"
                )
            }
        }
    }
}

@Composable
fun StopwatchIconButton(
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)
            .background(
                color = Color.White.copy(alpha = 0.8f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(40.dp),
            tint = Color.Black
        )
    }
}

fun formatTime(timeInMillis: Long): String {
    val minutes = (timeInMillis / 1000) / 60
    val seconds = (timeInMillis / 1000) % 60
    val milliseconds = (timeInMillis % 1000) / 10
    return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
}
