package com.example.roommvvmhiltcoroutinesretrofit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "employee_id")
    val id: Int,
    @ColumnInfo(name = "employee_name")
    val employee_name: String,
    @ColumnInfo(name = "employee_salary")
    val employee_salary: String,
    @ColumnInfo(name = "employee_age")
    val employee_age: String,
    @ColumnInfo(name = "profile_image")
    val profile_image: String

)