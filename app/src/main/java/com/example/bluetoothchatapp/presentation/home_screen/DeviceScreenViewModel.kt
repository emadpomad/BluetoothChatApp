package com.example.bluetoothchatapp.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothchatapp.data.BluetoothIsDisabledException
import com.example.bluetoothchatapp.data.BluetoothPermissionRequiredException
import com.example.bluetoothchatapp.domain.BluetoothController
import com.example.bluetoothchatapp.util.RequestedPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DeviceScreenViewModel @Inject constructor(
    private val bluetoothController: BluetoothController
) : ViewModel() {


    private val _uiState = MutableStateFlow(DeviceScreenUiState())
    val uiState =
        combine(
            _uiState, bluetoothController.availableDevices, bluetoothController.pairedDevices
        ) { state, available, paired ->

            state.copy(
                pairedDevices = paired,
                availableDevices = available
            )

        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), DeviceScreenUiState())


    fun onEvent(event: DeviceScreenUiEvent) {
        when (event) {
            is DeviceScreenUiEvent.OnDialogClosed -> {
                _uiState.value = _uiState.value.copy(
                    dialogQueue = _uiState.value.dialogQueue.toMutableList().apply {
                        removeFirst()
                    }.toList()
                )
            }

            is DeviceScreenUiEvent.OnPermissionResult -> {
                if (!event.isGranted) {
                    _uiState.value = _uiState.value.copy(
                        dialogQueue = _uiState.value.dialogQueue.toMutableList().apply {
                            add(event.permission)
                        }.toList(),
                        permissionToAsk = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        permissionToAsk = null
                    )
                }
            }

            is DeviceScreenUiEvent.OnStartDiscovery -> {
                updatePairedDevices()
                startDiscovery()
            }

            is DeviceScreenUiEvent.OnStopDiscovery -> {
                stopDiscovery()
            }

            is DeviceScreenUiEvent.OnOpened -> {
                Log.d("OnOpened",uiState.value.dialogQueue.toString())
                updatePairedDevices()
            }

        }
    }


    private fun updatePairedDevices() {
        _uiState.value = _uiState.value.copy(
            shouldEnableBluetooth = false
        )
        try {
            bluetoothController.updatePairedDevices()
        } catch (e: BluetoothPermissionRequiredException) {

            _uiState.value = _uiState.value.copy(
                permissionToAsk = RequestedPermission.BluetoothScan
            )

        } catch (e: BluetoothIsDisabledException) {

            _uiState.value = _uiState.value.copy(
                shouldEnableBluetooth = true
            )

        } catch (e: Exception) {
            Log.d("UpdatePairedDevices", e.message.toString())
        }
    }

    private fun startDiscovery() {
        _uiState.value = _uiState.value.copy(
            shouldEnableBluetooth = false
        )
        try {
            bluetoothController.startDiscovery()
        } catch (e: BluetoothPermissionRequiredException) {

            _uiState.value = _uiState.value.copy(
                permissionToAsk = RequestedPermission.BluetoothScan
            )

        } catch (e: BluetoothIsDisabledException) {

            _uiState.value = _uiState.value.copy(
                shouldEnableBluetooth = true
            )

        } catch (e: Exception) {
            Log.d("StartDiscovery", e.message.toString())
        }
    }

    private fun stopDiscovery() {
        _uiState.value = _uiState.value.copy(
            shouldEnableBluetooth = false
        )
        try {
            bluetoothController.stopDiscovery()
        } catch (e: BluetoothPermissionRequiredException) {

            _uiState.value = _uiState.value.copy(
                permissionToAsk = RequestedPermission.BluetoothScan
            )

        } catch (e: BluetoothIsDisabledException) {

            _uiState.value = _uiState.value.copy(
                shouldEnableBluetooth = true
            )

        } catch (e: Exception) {
            Log.d("StopDiscovery", e.message.toString())
        }
    }


}
