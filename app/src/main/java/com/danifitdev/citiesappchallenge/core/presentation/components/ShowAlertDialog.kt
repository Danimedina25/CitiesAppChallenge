package com.danifitdev.citiesappchallenge.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.Black
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.Gray
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.Red

@Composable
fun ShowCustomDialog(
    titulo: String? = null,
    mensaje: String,
    showDialog: Boolean,
    onDismiss: ()-> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    if(!titulo.isNullOrEmpty()){
                        Text(
                            text = titulo,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Red,
                            modifier = Modifier.padding(bottom = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    Text(
                        text = mensaje,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(containerColor = Gray)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}