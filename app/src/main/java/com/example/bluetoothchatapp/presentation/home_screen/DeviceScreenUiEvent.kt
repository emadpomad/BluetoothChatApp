package com.example.bluetoothchatapp.presentation.home_screen

import com.example.bluetoothchatapp.util.RequestedPermission

sealed class DeviceScreenUiEvent {

    class OnDialogClosed() : DeviceScreenUiEvent()
    class OnPermissionResult(
        val permission: RequestedPermission,
        val isGranted: Boolean
    ) : DeviceScreenUiEvent()

    class OnStartDiscovery() : DeviceScreenUiEvent()
    class OnStopDiscovery() : DeviceScreenUiEvent()

    class OnOpened() : DeviceScreenUiEvent()

}
