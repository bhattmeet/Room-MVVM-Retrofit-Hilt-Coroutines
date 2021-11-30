package com.example.roommvvmhiltcoroutinesretrofit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roommvvmhiltcoroutinesretrofit.models.Employee

/**
 * The class should be an abstract class and extend RoomDatabase.
 * Employee class annotated with @Entity.
 * There is no limit on the number of Entity or Dao classes but they must be unique within the Database.
 */
@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase: RoomDatabase(){

    private var INSTANCE: EmployeeDatabase? = null

    open fun getInstance(context: Context): EmployeeDatabase? {
        if (INSTANCE == null){
            synchronized(EmployeeDatabase::class.java){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "Employee_DB"
                    ).build()
                }
            }
        }
        return INSTANCE
    }

    abstract fun employeeDao(): EmployeeDao
}