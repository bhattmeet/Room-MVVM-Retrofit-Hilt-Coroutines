package com.example.roommvvmhiltcoroutinesretrofit.util

sealed class ApiState {

    object Loading : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Success<R>(val result: R) : ApiState()
    object Empty : ApiState()
}