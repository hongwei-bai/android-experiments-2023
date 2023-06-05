package com.example.exp23.data.model

import android.util.Log
import com.example.exp23.data.remote.OpenApiService
import com.example.exp23.utility.Base64Utils
import com.google.gson.Gson
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val openApiService: OpenApiService
) {
    suspend fun test() {
        val response = try {
            openApiService.getApiContent()
        } catch (e: Exception) {
            null
        }
        val body = response?.body()
        Log.d("bbbb", "response: $response")
        Log.d("bbbb", "body: $body")
        if (response?.isSuccessful == true && body != null) {
            delay(1)
            val encodedContent = body.content
            val decoded = Base64Utils.decodeBase64ToString(encodedContent)
            Log.d("bbbb", "decoded: $decoded")
            val model = Gson().fromJson(decoded, List::class.java)
            Log.d("bbbb", "model: $model")
        }
    }
}