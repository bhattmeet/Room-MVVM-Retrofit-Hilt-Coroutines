package com.example.roommvvmhiltcoroutinesretrofit.di

import android.content.Context
import androidx.room.Room
import com.example.roommvvmhiltcoroutinesretrofit.database.EmployeeDao
import com.example.roommvvmhiltcoroutinesretrofit.database.EmployeeDatabase
import com.example.roommvvmhiltcoroutinesretrofit.network.ApiService
import com.example.roommvvmhiltcoroutinesretrofit.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private var logging = HttpLoggingInterceptor()

    @Provides
    fun providesEmployeeDao(employeeDatabase: EmployeeDatabase): EmployeeDao =
        employeeDatabase.employeeDao()

    @Provides
    @Singleton
    fun providesEmployeeDatabase(@ApplicationContext context: Context): EmployeeDatabase =
        Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java,
            "EmployeeDB"
        ).allowMainThreadQueries().build()

    @Provides
    fun provideUrl() = BASE_URL

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient, url: String): ApiService =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}