package com.example.edvora_android_assignment.common.network

class ApiClient (private val APIService: ApiService): BaseApiClient() {

    suspend fun getData() = getResult {
        APIService.getDataFromApi()
    }
}
