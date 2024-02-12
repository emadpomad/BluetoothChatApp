package com.example.bluetoothchatapp.domain

import com.example.bluetoothchatapp.domain.model.BluetoothDeviceModel
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val pairedDevices: StateFlow<List<BluetoothDeviceModel>>
    val availableDevices: StateFlow<List<BluetoothDeviceModel>>

    fun startDiscovery()
    fun stopDiscovery()
    fun release()
    fun updatePairedDevices()

}
