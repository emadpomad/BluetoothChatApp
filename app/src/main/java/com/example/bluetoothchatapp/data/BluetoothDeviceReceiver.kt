package com.example.bluetoothchatapp.data

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class BluetoothDeviceReceiver @AssistedInject constructor(
    @Assisted val onDeviceFound: (device: BluetoothDevice) -> Unit
) : BroadcastReceiver() {


    @AssistedFactory
    interface BluetoothDeviceReceiverFactory {
        fun create(onDeviceFound: (device: BluetoothDevice) -> Unit):
                BluetoothDeviceReceiver
    }


    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == BluetoothDevice.ACTION_FOUND) {
            val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(
                    BluetoothDevice.EXTRA_DEVICE,
                    BluetoothDevice::class.java
                )
            } else {
                intent.getParcelableExtra(
                    BluetoothDevice.EXTRA_DEVICE,
                )
            }

            onDeviceFound(device ?: return)

        }

    }

}
