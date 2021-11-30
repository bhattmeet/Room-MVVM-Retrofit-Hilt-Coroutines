package com.example.roommvvmhiltcoroutinesretrofit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roommvvmhiltcoroutinesretrofit.repository.MainRepository
import com.example.roommvvmhiltcoroutinesretrofit.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    val apiStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    fun getEmployeeList() = viewModelScope.launch {
        apiStateFlow.value = ApiState.Loading
        mainRepository.getEmployeeApiData()
            .catch { e ->
                apiStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                apiStateFlow.value = ApiState.Success(data)
            }
    }
}