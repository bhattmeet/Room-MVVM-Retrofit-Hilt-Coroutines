package com.example.roommvvmhiltcoroutinesretrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roommvvmhiltcoroutinesretrofit.models.Employee
import com.example.roommvvmhiltcoroutinesretrofit.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(private val employeeRepository: EmployeeRepository): ViewModel(){

    val getList: LiveData<List<Employee>> get() =
        employeeRepository.getData.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)

    fun insert(employee: Employee){
        viewModelScope.launch {
            employeeRepository.insert(employee)
        }
    }

    fun delete(employee: Employee){
        viewModelScope.launch {
            employeeRepository.delete(employee)
        }
    }
}