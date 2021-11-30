package com.example.roommvvmhiltcoroutinesretrofit.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roommvvmhiltcoroutinesretrofit.R
import com.example.roommvvmhiltcoroutinesretrofit.adapter.EmployeeListAdapter
import com.example.roommvvmhiltcoroutinesretrofit.databinding.ActivityMainBinding
import com.example.roommvvmhiltcoroutinesretrofit.models.Employee
import com.example.roommvvmhiltcoroutinesretrofit.models.EmployeeResponse
import com.example.roommvvmhiltcoroutinesretrofit.util.ApiState
import com.example.roommvvmhiltcoroutinesretrofit.util.Utilities
import com.example.roommvvmhiltcoroutinesretrofit.util.Utilities.showToast
import com.example.roommvvmhiltcoroutinesretrofit.viewModel.EmployeeViewModel
import com.example.roommvvmhiltcoroutinesretrofit.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EmployeeListAdapter.OnItemClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var employeeListAdapter: EmployeeListAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var context: Context
    private val employeeViewModel: EmployeeViewModel by viewModels()
    private var employeeList: List<Employee> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViews()
        initializeData()
    }

    private fun initializeViews() {
        context = this
        employeeListAdapter = EmployeeListAdapter(employeeList, this, context)
        binding.rvEmployee.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = employeeListAdapter
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) =
                deleteItemFromDb(h.adapterPosition)
        }).attachToRecyclerView(binding.rvEmployee)
    }

    private fun deleteItemFromDb(adapterPosition: Int) {
        employeeViewModel.delete(employeeList[adapterPosition])
        lifecycleScope.launch {
            delay(1000L)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeData() {
        employeeViewModel.getList.observe(this, { response ->
            employeeList = (response as ArrayList<Employee>)
            if (employeeList.isNotEmpty()) {
                //load from db
                binding.rvEmployee.isVisible = true
                employeeListAdapter.setData(response)
                employeeListAdapter.notifyDataSetChanged()
            } else {
                // get data from server
                callApi()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callApi() {
//        val mHashMap = HashMap<String, Any>()
//        mHashMap["name"] = "morpheus"
//        mHashMap["page"] = "leader"
        mainViewModel.getEmployeeList()
        lifecycleScope.launchWhenStarted {
            mainViewModel.apiStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                    }
                    is ApiState.Failure -> {
                        showToast(context, it.msg.toString())
                    }
                    is ApiState.Success<*> -> {
                        binding.rvEmployee.isVisible = true
                        var result = it.result as EmployeeResponse
                        employeeList = result.data
                        employeeListAdapter.setData(result.data)
                        employeeListAdapter.notifyDataSetChanged()
                        for (i in 0 until result.data.size) {
                            //saving to db
                            employeeViewModel.insert(result.data[i])
                        }
                    }
                    is ApiState.Empty -> {
                    }
                }
            }
        }
    }

    override fun onItemClicked(data: Employee) {
        showToast(context,"Clicked ${data.employee_name}")
    }
}