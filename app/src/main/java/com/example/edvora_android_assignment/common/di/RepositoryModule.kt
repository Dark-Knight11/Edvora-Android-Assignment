package com.example.edvora_android_assignment.common.di

import com.example.edvora_android_assignment.common.database.dao.ProductDao
import com.example.edvora_android_assignment.common.network.ApiClient
import com.example.edvora_android_assignment.products.data.ProductsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepo(apiClient: ApiClient, dao: ProductDao): ProductsRepo {
        return ProductsRepo(apiClient, dao)
    }
}