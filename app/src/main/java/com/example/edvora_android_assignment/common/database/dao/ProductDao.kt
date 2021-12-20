package com.example.edvora_android_assignment.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.edvora_android_assignment.common.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = REPLACE)
    suspend fun addProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getProduct(): Flow<List<Product>>

    @Query("SELECT DISTINCT state FROM product")
    fun getStates(): Flow<List<String>>

    @Query("SELECT DISTINCT city FROM product")
    fun getCities(): Flow<List<String>>

    @Query("SELECT * FROM product WHERE city=:city")
    fun filterByCity(city: String): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE state=:state")
    fun filterByState(state: String): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE productName LIKE :category")
    fun filterByCategory(category: String): Flow<List<Product>>

}