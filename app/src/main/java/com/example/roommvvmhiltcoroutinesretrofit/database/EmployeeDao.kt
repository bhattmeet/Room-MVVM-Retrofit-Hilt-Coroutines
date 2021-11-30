package com.example.roommvvmhiltcoroutinesretrofit.database

import androidx.room.*
import com.example.roommvvmhiltcoroutinesretrofit.models.Employee
import kotlinx.coroutines.flow.Flow

/**
 * Dao - Data Access Object
 * In this class we define our database interactions.
 * It's include variety of query methods.
 * At compile time, Room will generate an implementation of this class when it is referenced by a Database.
 * Insert, Delete, Update, Query
 */
@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employee ORDER BY employee_age ASC")
    fun getEmployeeList(): Flow<List<Employee>>

    @Delete
    fun delete(employee: Employee)
}