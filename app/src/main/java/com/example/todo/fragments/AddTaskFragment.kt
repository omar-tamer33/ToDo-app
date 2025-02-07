package com.example.todo.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.callbacks.OnTaskAddedListener
import com.example.todo.database.model.Task
import com.example.todo.database.model.TaskDatabase
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date

class AddTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var calendar: Calendar
    var onTaskAddedListener: OnTaskAddedListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = Calendar.getInstance()
        binding.addTaskBtn.setOnClickListener {
            if (validateInput()){
                addTask()
            }
        }

        binding.selectDateTv.setOnClickListener{
            showDate()
        }
    }

    private fun showDate() {
        val datePicker = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.setDate(year,month,dayOfMonth)
            calendar.clearTime()
            binding.selectDateTv.text = "$dayOfMonth/${month+1}/$year"

        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.minDate = Date().time
        datePicker.show()
    }

    private fun addTask() {
        if (isHidden) return
        TaskDatabase.getInstance().getTaskDao().insertTask(Task(title = binding.title.text.toString(), date = calendar.time , isDone = false))
        onTaskAddedListener?.onTaskAdded()
        dismiss()

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