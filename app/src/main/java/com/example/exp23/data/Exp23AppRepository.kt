package com.example.exp23.data

import com.example.exp23.data.model.Exp23AppModel
import com.example.exp23.data.model.Exp23AppModelBase
import com.example.exp23.data.model.Exp23AppModelError
import com.example.exp23.data.remote.OpenApiService
import com.example.exp23.utility.Base64Utils.decodeBase64ToString
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Exp23AppRepository @Inject constructor(
    private val openApiService: OpenApiService
) {
    private val channel = Channel<Exp23AppModelBase>()
    val flow = channel.receiveAsFlow()

    suspend fun fetchData(initFlag: Boolean = true) {
        val response = try {
            if (initFlag) {
                openApiService.getApiData()
            } else {
                openApiService.getApiData2()
            }
        } catch (e: Exception) {
            null
        }
        val body = response?.body()
        if (response?.isSuccessful == true && body != null) {
            delay(500)
            val encodedContent = body.content
            val model =
                Gson().fromJson(decodeBase64ToString(encodedContent), Exp23AppModel::class.java)
            channel.send(model)
        } else {
            channel.send(Exp23AppModelError)
        }
    }
}