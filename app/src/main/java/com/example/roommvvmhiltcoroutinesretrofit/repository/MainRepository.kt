package com.example.roommvvmhiltcoroutinesretrofit.repository

import com.example.roommvvmhiltcoroutinesretrofit.models.EmployeeResponse
import com.example.roommvvmhiltcoroutinesretrofit.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getEmployeeApiData(): Flow<EmployeeResponse> = flow {
        emit(apiServiceImpl.getEmployeeList())
    }.flowOn(Dispatchers.IO)
}