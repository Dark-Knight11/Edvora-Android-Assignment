package com.example.edvora_android_assignment.products.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edvora_android_assignment.common.models.Product
import com.example.edvora_android_assignment.common.network.Resource
import com.example.edvora_android_assignment.products.data.ProductsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class ProductViewModel @Inject constructor(private val repo: ProductsRepo) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean>
        get() = _error

    val product = repo.getProducts
    val state = repo.getStates
    val city = repo.getCities
    val shoesData = repo.filterByCategory("%Shoes%")
    val appleData = repo.filterByCategory("%Apple%")
    val teslaData = repo.filterByCategory("%Tesla%")
    val oilData = repo.filterByCategory("%Oil%")
    private val _filteredData: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    val filteredData: MutableStateFlow<List<Product>>
            get() = _filteredData

    suspend fun filterByCity(city: String) = repo.filterByCity(city).collect {
        _filteredData.value = it
    }

    suspend fun filterByState(state: String) = repo.filterByState(state).collect {
        _filteredData.value = it
    }

    suspend fun filterByCategory(category: String) = repo.filterByCategory("%${category}%").collect {
        _filteredData.value = it
    }

    init {
        viewModelScope.launch {
            refreshProducts()
        }
    }


    suspend fun refreshProducts() = repo.refreshProducts().collect { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                _loading.value = false
            }
            Resource.Status.ERROR -> {
                _error.value = true
                _loading.value = false
            }
            Resource.Status.LOADING -> {
                _loading.value = true
            }
        }
    }
}


