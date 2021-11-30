package com.example.roommvvmhiltcoroutinesretrofit.network

import com.example.roommvvmhiltcoroutinesretrofit.models.EmployeeResponse
import retrofit2.http.GET

/**
 * It's interface where we add @POST and @GET annotation to make request.
 */
interface ApiService {

    @GET("employees")
    suspend fun getEmployeeList(): EmployeeResponse
}