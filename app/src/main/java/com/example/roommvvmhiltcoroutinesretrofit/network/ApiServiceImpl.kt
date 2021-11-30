package com.example.roommvvmhiltcoroutinesretrofit.network

import com.example.roommvvmhiltcoroutinesretrofit.models.EmployeeResponse
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getEmployeeList(): EmployeeResponse = apiService.getEmployeeList()
}