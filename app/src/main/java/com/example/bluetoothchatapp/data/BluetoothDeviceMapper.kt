package com.example.bluetoothchatapp.data

import android.bluetooth.BluetoothDevice
import com.example.bluetoothchatapp.domain.model.BluetoothDeviceModel

fun BluetoothDevice.toBluetoothDeviceModel(): BluetoothDeviceModel {
    return BluetoothDeviceModel(
        name ?: "Unknown Device",
        address = address
    )
}
