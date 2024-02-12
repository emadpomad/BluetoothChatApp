package com.example.bluetoothchatapp.presentation.home_screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionExplanation:String,
    isPermanentlyDeclined: Boolean = false,
    onOkClicked: () -> Unit,
    onDismissed: () -> Unit,
    onGoToAppSettings: () -> Unit
) {


    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                text = "Permission required",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = permissionExplanation,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        },
        onDismissRequest = { onDismissed() },
        confirmButton = {
            Button(
                onClick = {
                    if (isPermanentlyDeclined) onGoToAppSettings() else onOkClicked()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (isPermanentlyDeclined) "Grant Permission" else "Ok"
                )
            }
        }
    )


}
