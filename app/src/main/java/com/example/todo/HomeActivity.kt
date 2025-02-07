package com.example.todo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todo.callbacks.OnTaskAddedListener
import com.example.todo.database.model.TaskDatabase
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.fragments.AddTaskFragment
import com.example.todo.fragments.SettingsFragment
import com.example.todo.fragments.TaskListFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var taskListFragment: TaskListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TaskDatabase.init(this)

        taskListFragment = TaskListFragment()

        changeFragment(taskListFragment)

        binding.addFab.setOnClickListener{
            val addTaskFragment = AddTaskFragment()
            addTaskFragment.onTaskAddedListener = OnTaskAddedListener {
                taskListFragment.getAllTasksFromDatabase()

            }
            addTaskFragment.show(supportFragmentManager,null)
        }

        binding.todoBottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.taskList -> changeFragment(taskListFragment)
                R.id.settings -> changeFragment(SettingsFragment())
            }

            return@setOnItemSelectedListener true
        }


    }

    private fun changeFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,fragment).commit()
    }
}