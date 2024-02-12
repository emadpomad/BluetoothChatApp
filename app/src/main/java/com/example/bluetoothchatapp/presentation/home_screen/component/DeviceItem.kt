package com.example.bluetoothchatapp.presentation.home_screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bluetoothchatapp.domain.model.BluetoothDeviceModel

@Composable
fun DeviceItem(
    modifier: Modifier = Modifier,
    device: BluetoothDeviceModel,
    onClick: () -> Unit
) {


    Row(
        modifier = modifier.clickable {
            onClick()
        }
    ) {

        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = device.name ?: "Unknown Device",
            style = MaterialTheme.typography.bodyLarge
        )

    }


}
