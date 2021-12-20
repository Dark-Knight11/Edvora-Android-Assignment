package com.example.edvora_android_assignment.common.di

import android.content.Context
import androidx.room.Room
import com.example.edvora_android_assignment.common.database.Database
import com.example.edvora_android_assignment.common.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "Product Data"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: Database):  ProductDao {
        return database.productDao
    }
}

