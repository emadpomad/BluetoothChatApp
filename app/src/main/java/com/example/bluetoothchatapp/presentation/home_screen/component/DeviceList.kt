package com.example.bluetoothchatapp.presentation.home_screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bluetoothchatapp.domain.model.BluetoothDeviceModel

@Composable
fun DeviceList(
    modifier: Modifier = Modifier,
    pairedDevices: List<BluetoothDeviceModel>,
    availableDevices: List<BluetoothDeviceModel>,
    onDeviceClicked: (device: BluetoothDeviceModel) -> Unit
) {

    Column(modifier = modifier) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            item {
                Text(
                    text = "Paired Devices",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (pairedDevices.isNotEmpty()) {
                items(pairedDevices) { device ->
                    DeviceItem(
                        modifier = Modifier.fillMaxWidth(),
                        device = device,
                        onClick = { onDeviceClicked(device) }
                    )
                    Divider(thickness = 1.dp, color = Color.DarkGray)

                }
            } else {
                item {
                    Text(
                        text = "No device available :(",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            item {
                Text(
                    text = "Scanned Devices",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (availableDevices.isNotEmpty()) {
                items(availableDevices) { device ->
                    DeviceItem(
                        modifier = Modifier.fillMaxWidth(),
                        device = device,
                        onClick = { onDeviceClicked(device) }
                    )
                    Divider(thickness = 1.dp, color = Color.DarkGray)

                }
            } else {
                item {
                    Text(
                        text = "No device available :(",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        }


    }

}
