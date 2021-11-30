package com.example.roommvvmhiltcoroutinesretrofit.models

data class EmployeeResponse(
    val data: ArrayList<Employee>,
    val message: String,
    val status: String
)