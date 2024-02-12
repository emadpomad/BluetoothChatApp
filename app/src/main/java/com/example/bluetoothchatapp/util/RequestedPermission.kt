package com.example.bluetoothchatapp.util

import android.Manifest

enum class RequestedPermission {
    BluetoothScan,
    BluetoothConnect
}

fun RequestedPermission.toManifestPermission(): String {
    return when (this) {
        RequestedPermission.BluetoothScan -> {
            Manifest.permission.BLUETOOTH_SCAN
        }

        RequestedPermission.BluetoothConnect -> {
            Manifest.permission.BLUETOOTH_SCAN
        }

    }
}
