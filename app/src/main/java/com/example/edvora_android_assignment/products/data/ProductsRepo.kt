package com.example.edvora_android_assignment.products.data

import com.example.edvora_android_assignment.common.database.dao.ProductDao
import com.example.edvora_android_assignment.common.network.ApiClient
import com.example.edvora_android_assignment.common.network.BaseRepo
import javax.inject.Inject

class ProductsRepo @Inject constructor(
    private val ApiClient: ApiClient,
    private val dao: ProductDao
): BaseRepo() {

    suspend fun refreshProducts() = refreshAndSave(
        networkCall = {
            ApiClient.getData()
        },
        saveCallResult = { products ->
            for (product in products)
                dao.addProduct(product)
        }
    )

    val getProducts = dao.getProduct()

    val getStates = dao.getStates()

    val getCities = dao.getCities()

    fun filterByCity(city: String) = dao.filterByCity(city)

    fun filterByState(state: String) = dao.filterByState(state)

    fun filterByCategory(category: String) = dao.filterByCategory(category)
}