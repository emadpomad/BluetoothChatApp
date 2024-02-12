package com.example.bluetoothchatapp.presentation

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bluetoothchatapp.presentation.home_screen.DeviceScreenUiEvent
import com.example.bluetoothchatapp.presentation.home_screen.DeviceScreenViewModel
import com.example.bluetoothchatapp.presentation.home_screen.component.DeviceScreen
import com.example.bluetoothchatapp.presentation.home_screen.component.PermissionDialog
import com.example.bluetoothchatapp.ui.theme.BluetoothChatAppTheme
import com.example.bluetoothchatapp.util.RequestedPermission.BluetoothConnect
import com.example.bluetoothchatapp.util.RequestedPermission.BluetoothScan
import com.example.bluetoothchatapp.util.toManifestPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val viewModel: DeviceScreenViewModel = hiltViewModel()
                    val state by viewModel.uiState.collectAsState()

                    val bluetoothScanPermissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            viewModel.onEvent(
                                DeviceScreenUiEvent.OnPermissionResult(
                                    BluetoothScan,
                                    isGranted
                                )
                            )
                        }
                    )

                    val bluetoothEnableLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult(),
                        onResult = {}
                    )


                    DeviceScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        state = state,
                        onEvent = { viewModel.onEvent(it) }
                    )


                    if (state.shouldEnableBluetooth) {
                        bluetoothEnableLauncher.launch(
                            Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE
                            )
                        )
                    }


                    state.permissionToAsk?.let { permission ->

                        when (permission) {
                            BluetoothScan -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    bluetoothScanPermissionLauncher.launch(
                                        Manifest.permission.BLUETOOTH_SCAN
                                    )
                                }
                            }

                            BluetoothConnect -> TODO()
                        }

                    }


                    state.dialogQueue.forEach { perm ->


                        PermissionDialog(
                            permissionExplanation = "Needed",
                            onOkClicked = {
                                viewModel.onEvent(
                                    DeviceScreenUiEvent.OnDialogClosed()
                                )

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    bluetoothScanPermissionLauncher.launch(
                                        perm.toManifestPermission()
                                    )
                                }

                            },
                            isPermanentlyDeclined = true,
                            onDismissed = {
                                viewModel.onEvent(
                                    DeviceScreenUiEvent.OnDialogClosed()
                                )
                            },
                            onGoToAppSettings = {
                                viewModel.onEvent(
                                    DeviceScreenUiEvent.OnDialogClosed()
                                )
                                goToAppSettings()
                            }
                        )


                    }


                    LaunchedEffect(true) {
                        viewModel.onEvent(
                            DeviceScreenUiEvent.OnOpened()
                        )
                    }
                }


            }
        }
    }
}

private fun Activity.goToAppSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )

    startActivity(intent)

}
