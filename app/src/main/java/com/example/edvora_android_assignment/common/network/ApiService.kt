package com.example.edvora_android_assignment.common.network

import com.example.edvora_android_assignment.common.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getDataFromApi(): Response<List<Product>>
}