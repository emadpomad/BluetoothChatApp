package com.example.bluetoothchatapp.presentation.home_screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bluetoothchatapp.presentation.home_screen.DeviceScreenUiEvent
import com.example.bluetoothchatapp.presentation.home_screen.DeviceScreenUiState

@Composable
fun DeviceScreen(
    state: DeviceScreenUiState,
    onEvent: (event: DeviceScreenUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            DeviceList(
                pairedDevices = state.pairedDevices,
                availableDevices = state.availableDevices,
                onDeviceClicked = {
                    //TODO
                }
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    onEvent(
                        DeviceScreenUiEvent.OnStartDiscovery()
                    )
                }
            ) {
                Text(text = "Start Discovery")
            }

            Button(
                onClick = {
                    onEvent(
                        DeviceScreenUiEvent.OnStopDiscovery()
                    )
                }
            ) {
                Text(text = "Stop Discovery")
            }
        }


    }


}
