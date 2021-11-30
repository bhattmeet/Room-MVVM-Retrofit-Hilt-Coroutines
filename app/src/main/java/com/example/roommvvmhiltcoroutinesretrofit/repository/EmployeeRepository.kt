package com.example.roommvvmhiltcoroutinesretrofit.repository

import androidx.annotation.WorkerThread
import com.example.roommvvmhiltcoroutinesretrofit.database.EmployeeDao
import com.example.roommvvmhiltcoroutinesretrofit.models.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    val getData: Flow<List<Employee>> = employeeDao.getEmployeeList()

    @WorkerThread
    suspend fun insert(employee: Employee) = withContext(Dispatchers.IO){
        employeeDao.insert(employee)
    }

    @WorkerThread
    suspend fun delete(employee: Employee) = withContext(Dispatchers.IO){
        employeeDao.delete(employee)
    }
}