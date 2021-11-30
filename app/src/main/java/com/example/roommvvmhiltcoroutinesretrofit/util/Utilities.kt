package com.example.roommvvmhiltcoroutinesretrofit.util

import android.content.Context
import android.widget.Toast

object Utilities {

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}