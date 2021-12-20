package com.example.edvora_android_assignment.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.edvora_android_assignment.common.database.dao.ProductDao
import com.example.edvora_android_assignment.common.models.Product

@Database(
    entities = [Product::class],
    exportSchema = false,
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val productDao: ProductDao
}