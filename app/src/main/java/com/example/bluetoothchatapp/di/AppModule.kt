package com.example.bluetoothchatapp.di

import com.example.bluetoothchatapp.data.BluetoothControllerImpl
import com.example.bluetoothchatapp.domain.BluetoothController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideBluetoothController(
        bluetoothControllerImpl: BluetoothControllerImpl
    ): BluetoothController

}
