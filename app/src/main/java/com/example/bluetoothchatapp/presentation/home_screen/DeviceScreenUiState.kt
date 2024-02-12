package com.example.bluetoothchatapp.presentation.home_screen

import com.example.bluetoothchatapp.domain.model.BluetoothDeviceModel
import com.example.bluetoothchatapp.util.RequestedPermission

data class DeviceScreenUiState(
    val pairedDevices: List<BluetoothDeviceModel> = emptyList(),
    val availableDevices: List<BluetoothDeviceModel> = emptyList(),
    val dialogQueue: List<RequestedPermission> = emptyList(),
    val permissionToAsk: RequestedPermission? = null,
    val shouldEnableBluetooth : Boolean = false

)
