package com.example.todo.database.model

import android.content.Context
import android.os.Build.VERSION
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.database.model.dao.TaskDao

@Database(entities = arrayOf(Task::class), version = 2)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object{
        var DATABASE_INSTANCE : TaskDatabase? = null
        fun init(applicationContext : Context){
            if (DATABASE_INSTANCE == null){
                DATABASE_INSTANCE = Room.databaseBuilder(applicationContext,TaskDatabase::class.java,"Tasks Database").allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
        }
        fun getInstance() : TaskDatabase{

            return DATABASE_INSTANCE!!
        }
    }
}