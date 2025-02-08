package com.example.todo

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.database.model.Task
import com.example.todo.database.model.TaskDatabase
import com.example.todo.database.model.dao.TaskDao
import com.example.todo.databinding.ActivityEditTaskBinding
import com.example.todo.fragments.TaskListFragment
import com.example.todo.fragments.clearTime
import com.example.todo.fragments.setDate
import java.util.Calendar
import java.util.Date

class EditTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTaskBinding
    lateinit var  intentTask : Task
    lateinit var newTask: Task
    lateinit var calendar : Calendar
    lateinit var dao: TaskDao
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = TaskDatabase.getInstance().getTaskDao()
        calendar = Calendar.getInstance()
       intentTask = IntentCompat.getParcelableExtra(intent,"task",Task::class.java) as Task
        newTask = intentTask.copy()
        binding.title.setText(intentTask.title)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.selectDateTv.text = "$day/${month+1}/$year"

        binding.selectDateTv.setOnClickListener {
            showDate()
        }

        binding.saveBtn.setOnClickListener {
            if (validateInput()){
                updateTask()
            }
        }

    }

    private fun updateTask() {
        newTask.apply {
            description = binding.description.text.toString()
            title = binding.title.text.toString()
            date = calendar.time
        }
        dao.updateTask(newTask)
        finish()
    }

    private fun showDate() {
        val datePicker = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.setDate(year,month,dayOfMonth)
            calendar.clearTime()
            binding.selectDateTv.text = "$dayOfMonth/${month+1}/$year"

        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.minDate = Date().time
        datePicker.show()
    }

    private fun validateInput(): Boolean {
        if (binding.title.text.isNullOrBlank() || binding.title.text.isNullOrEmpty()){
            binding.titleTil.error = getString(R.string.required)
            return false
        }else{
            binding.titleTil.error = null
            return true
        }
    }
}