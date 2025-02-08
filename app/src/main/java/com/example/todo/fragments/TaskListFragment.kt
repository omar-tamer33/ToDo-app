package com.example.todo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.EditTaskActivity
import com.example.todo.R
import com.example.todo.adapters.TaskAdapter
import com.example.todo.database.model.TaskDatabase
import com.example.todo.databinding.FragmentTaskListBinding
import com.example.todo.viewContanier.CustomWeekDayBinder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

class TaskListFragment : Fragment() {
    lateinit var binding: FragmentTaskListBinding
    lateinit var adapter: TaskAdapter
    lateinit var weekDayBinder: CustomWeekDayBinder
    lateinit var calendar: Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        weekDayBinder = CustomWeekDayBinder(selectedColor = resources.getColor(R.color.blue,null), unSelectedColor = resources.getColor(R.color.black,null)){ weekDay ->
            val currentSelection = weekDayBinder.selectedDate
            if (currentSelection == weekDay.date){
                weekDayBinder.selectedDate = null
                getAllTasksFromDatabase()
                binding.calendarRv.notifyDateChanged(currentSelection)
            }else{
                weekDayBinder.selectedDate = weekDay.date
                calendar.setDate(weekDay.date.year,weekDay.date.monthValue-1,weekDay.date.dayOfMonth)
                calendar.clearTime()
                getTasksByDateFromDatabase(calendar.time)
                binding.calendarRv.notifyDateChanged(weekDay.date)
                if (currentSelection != null){
                    binding.calendarRv.notifyDateChanged(currentSelection)
                }
            }
        }
        binding.calendarRv.dayBinder = weekDayBinder
        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = LocalDate.now()// Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = DayOfWeek.SATURDAY // Available from the library
        binding.calendarRv.setup(startDate, endDate, firstDayOfWeek)
        binding.calendarRv.scrollToWeek(currentDate)
        adapter = TaskAdapter()
        binding.taskRv.adapter = adapter
        getAllTasksFromDatabase()
        adapter.onDeleteClickListener = TaskAdapter.OnTaskClickListener{position, task ->
            TaskDatabase.getInstance().getTaskDao().deleteTask(task)
            adapter.delete(position,task)
        }

        adapter.onDoneBtnClickListener = TaskAdapter.OnTaskClickListener{position, task ->
            task.isDone=!task.isDone
            TaskDatabase.getInstance().getTaskDao().updateTask(task)
            adapter.updtaeTask(position,task)

        }

        adapter.onItemClickListener = TaskAdapter.OnTaskClickListener { position, task ->
            val intent = Intent(requireContext(),EditTaskActivity::class.java)
            intent.putExtra("task",task)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        getAllTasksFromDatabase()
    }

    fun getAllTasksFromDatabase(){
      val taskList = TaskDatabase.getInstance().getTaskDao().getAllTasks()
        adapter.taskList = taskList
        adapter.notifyDataSetChanged()
    }

    fun getTasksByDateFromDatabase(date: Date){
        val list = TaskDatabase.getInstance().getTaskDao().getTaskByDate(date)
        adapter.taskList = list
        adapter.notifyDataSetChanged()
    }
}