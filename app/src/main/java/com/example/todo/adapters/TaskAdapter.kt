package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.database.model.Task
import com.example.todo.databinding.ItemTaskBinding

class TaskAdapter(var taskList : List<Task>? = null) : Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater,parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = taskList?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = taskList?.get(position) ?: return
        holder.bind(item)
    }

    inner class TaskViewHolder(val binding : ItemTaskBinding) : ViewHolder(binding.root){
        fun bind(task : Task){
            binding.titleTv.text = task.title.toString()
            binding.dateTv.text = task.date.toString()

        }

    }
}