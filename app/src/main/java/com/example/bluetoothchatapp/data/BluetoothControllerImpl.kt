package com.example.bluetoothchatapp.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import com.example.bluetoothchatapp.domain.BluetoothController
import com.example.bluetoothchatapp.domain.model.BluetoothDeviceModel
import com.example.bluetoothchatapp.util.RequestedPermission
import com.example.bluetoothchatapp.util.toManifestPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@SuppressLint("MissingPermission")
class BluetoothControllerImpl @Inject constructor(
    @ApplicationContext val context: Context,
    bluetoothDeviceReceiverFactory: BluetoothDeviceReceiver.BluetoothDeviceReceiverFactory
) : BluetoothController {

    private val _pairedDevices: MutableStateFlow<List<BluetoothDeviceModel>> =
        MutableStateFlow(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceModel>>
        get() = _pairedDevices.asStateFlow()


    private val _availableDevices: MutableStateFlow<List<BluetoothDeviceModel>> =
        MutableStateFlow(emptyList())
    override val availableDevices: StateFlow<List<BluetoothDeviceModel>>
        get() = _availableDevices.asStateFlow()


    private val bluetoothManager = context.getSystemService(
        BluetoothManager::class.java
    )
    private val bluetoothAdapter = bluetoothManager.adapter

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter.isEnabled

    private val hasPermission: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.checkSelfPermission(
                RequestedPermission.BluetoothScan.toManifestPermission()
            ) == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(
                RequestedPermission.BluetoothConnect.toManifestPermission()
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }


    private val bluetoothDeviceReceiver: BluetoothDeviceReceiver =
        bluetoothDeviceReceiverFactory.create(onDeviceFound = { device ->
            _availableDevices.update { devices ->
                if (device.toBluetoothDeviceModel() in devices) {
                    devices
                } else {
                    devices + device.toBluetoothDeviceModel()
                }
            }
        })


    override fun startDiscovery() {

        _availableDevices.value = emptyList()

        if (!hasPermission) {
            throw BluetoothPermissionRequiredException()
        }


        if (!isBluetoothEnabled) {
            throw BluetoothIsDisabledException()
        }

        context.registerReceiver(
            bluetoothDeviceReceiver, IntentFilter(
                BluetoothDevice.ACTION_FOUND
            )
        )

        bluetoothAdapter.startDiscovery()


    }

    override fun stopDiscovery() {
        bluetoothAdapter.cancelDiscovery()
        release()
    }

    override fun release() {
        context.unregisterReceiver(
            bluetoothDeviceReceiver
        )
    }

    override fun updatePairedDevices() {

        if (!isBluetoothEnabled) {
            throw BluetoothIsDisabledException()
        }

        if (!hasPermission) {
            throw BluetoothPermissionRequiredException()
        }

        _pairedDevices.update {
            bluetoothAdapter.bondedDevices.map {
                it.toBluetoothDeviceModel()
            }
        }

    }
}


class BluetoothIsDisabledException() :
    Exception("Bluetooth feature is needed to continue")

class BluetoothPermissionRequiredException :
    Exception("Bluetooth permission is required to continue")
