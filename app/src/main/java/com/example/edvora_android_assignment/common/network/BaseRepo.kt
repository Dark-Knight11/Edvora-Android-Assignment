package com.example.edvora_android_assignment.common.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepo {
    protected suspend fun <T> refreshAndSave(
        networkCall: suspend () -> Resource<T>,
        saveCallResult: suspend (T) -> Unit
    ) = flow<Resource<T>>{
        emit(Resource.loading())
        val response = networkCall.invoke()
        when (response.status) {
            Resource.Status.SUCCESS -> {
                response.data?.let { saveCallResult.invoke(it) }
                if(response.data != null)
                    emit(Resource.success(response.data))
                else
                    emit(Resource.error("Something Went Wrong"))
            }
            Resource.Status.ERROR -> {
                // add logs
                emit(Resource.error("Something Went Wrong"))
            }
            else -> {}
        }
    }.flowOn(Dispatchers.IO)

}
