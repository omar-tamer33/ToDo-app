package com.example.todo.database.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.database.model.Task
import java.util.Date
@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Update
    fun updateTask(task: Task)
    @Query("SELECT * FROM Task")
    fun getAllTasks(): MutableList<Task>
    @Query("SELECT * FROM Task WHERE date = :selectedDate")
    fun getTaskByDate(selectedDate: Date): MutableList<Task>
}