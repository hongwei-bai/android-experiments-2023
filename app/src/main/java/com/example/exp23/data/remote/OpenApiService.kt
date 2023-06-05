package com.example.exp23.data.remote

import com.example.exp23.constant.CONTENT_ENDPOINT
import com.example.exp23.constant.MAIN_ENDPOINT
import com.example.exp23.constant.MAIN_ENDPOINT2
import retrofit2.Response
import retrofit2.http.GET

interface OpenApiService {
    @GET(MAIN_ENDPOINT)
    suspend fun getApiData(): Response<GithubOpenApiResponse>

    @GET(MAIN_ENDPOINT2)
    suspend fun getApiData2(): Response<GithubOpenApiResponse>

    @GET(CONTENT_ENDPOINT)
    suspend fun getApiContent(): Response<GithubOpenApiResponse>
}