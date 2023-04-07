package com.example.exp23.data.remote

import com.example.exp23.constant.FUEL_APP_MAIN_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface OpenApiService {
    @GET(FUEL_APP_MAIN_ENDPOINT)
    suspend fun getFuelAppData(): Response<GithubOpenApiResponse>
}