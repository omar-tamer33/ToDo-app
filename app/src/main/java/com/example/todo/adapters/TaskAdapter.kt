package com.example.todo.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.R
import com.example.todo.database.model.Task
import com.example.todo.databinding.ItemTaskBinding
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener

class TaskAdapter(var taskList : MutableList<Task>? = null) : Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater,parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = taskList?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = taskList?.get(position) ?: return
        holder.bind(item)
        holder.bindIsDoneStatus(item.isDone)
        holder.binding.leftView.isClickable = true
        holder.binding.swipeLayout.close()
        onDeleteClickListener.let {
            holder.binding.swipeLayout.setOnActionsListener(object : SwipeActionsListener{
                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if (direction == SwipeLayout.RIGHT){
                        holder.binding.leftView.setOnClickListener {
                            onDeleteClickListener?.onClick(holder.adapterPosition,item)
                        }
                    }
                }

                override fun onClose() {
                    holder.binding.leftView.isClickable = false
                }

            })
        }
        onDoneBtnClickListener?.let {
            holder.binding.btnTaskIsDone.setOnClickListener {
                onDoneBtnClickListener?.onClick(position,item)
            }
        }
    }

    fun delete(position: Int , task: Task){
        if (taskList?.indices?.contains(position) == true){
            taskList?.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,taskList!!.size-position)
        }
    }

    fun updtaeTask(position: Int,task: Task){
        if (taskList?.indices?.contains(position) == true){
            taskList!![position]=task
            notifyItemChanged(position)
        }
    }

    inner class TaskViewHolder(val binding : ItemTaskBinding) : ViewHolder(binding.root){
        fun bind(task : Task){
            binding.title.text = task.title.toString()
            binding.time.text = task.date.toString()
        }

        fun bindIsDoneStatus(isDone: Boolean){
            if (isDone){
                binding.draggingBar.setImageResource(R.drawable.dragging_bar_done)
                binding.title.setTextColor(Color.GREEN)
                binding.btnTaskIsDone.setBackgroundResource(R.drawable.done)
            }else{
                binding.draggingBar.setImageResource(R.drawable.dragging_bar)
                val blue = ContextCompat.getColor(itemView.context,R.color.blue)
                binding.title.setTextColor(blue)
                binding.btnTaskIsDone.setBackgroundResource(R.drawable.ic_isdone)
            }
        }

    }
    var onDeleteClickListener:OnTaskClickListener?=null

    var onDoneBtnClickListener : OnTaskClickListener?=null

   fun interface OnTaskClickListener{
        fun onClick(position: Int , task: Task)
    }
}