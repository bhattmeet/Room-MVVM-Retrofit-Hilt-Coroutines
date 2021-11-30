package com.example.roommvvmhiltcoroutinesretrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roommvvmhiltcoroutinesretrofit.databinding.RowEmployeeListBinding
import com.example.roommvvmhiltcoroutinesretrofit.models.Employee

class EmployeeListAdapter(
    private var employeeList: List<Employee>,
    private val itemClickListener: OnItemClickListener,
    var context: Context
    ) : RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder>()
{
    private lateinit var rowEmployeeListBinding: RowEmployeeListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        rowEmployeeListBinding = RowEmployeeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(rowEmployeeListBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val info = employeeList[position]
        holder.rowEmployeeListBinding.info = info
        holder.rowEmployeeListBinding.executePendingBindings()
        holder.bind(info,itemClickListener)
    }

    override fun getItemCount(): Int = employeeList.size

    class MyViewHolder(var rowEmployeeListBinding: RowEmployeeListBinding) :
        RecyclerView.ViewHolder(rowEmployeeListBinding.root) {

        fun bind(data: Employee, itemClickListener: OnItemClickListener) {
            rowEmployeeListBinding.cvEmployee.setOnClickListener {
                itemClickListener.onItemClicked(data)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(data: Employee)
    }

    fun setData(response: List<Employee>) {
        this.employeeList = response
    }
}