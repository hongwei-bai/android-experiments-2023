package com.example.exp23.data

import com.example.exp23.data.model.FuelAppModelBase
import com.example.exp23.data.model.FuelAppModelError
import com.example.exp23.data.remote.OpenApiService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FuelAppRepository @Inject constructor(
    private val openApiService: OpenApiService
) {
    private val fuelAppModelChannel = Channel<FuelAppModelBase>()
    val fuelAppModelFlow = fuelAppModelChannel.receiveAsFlow()

    suspend fun fetchFuelAppData() {
        val response = try {
            openApiService.getFuelAppData()
        } catch (e: Exception) {
            null
        }
        val body = response?.body()
        if (response?.isSuccessful == true && body != null) {
            delay(2000)
        } else {
            fuelAppModelChannel.send(FuelAppModelError)
        }
    }
}