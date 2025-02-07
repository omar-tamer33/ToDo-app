package com.example.todo.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var date: Date? = null,
    var isDone: Boolean
){
    @Ignore
    var description : String? = null
}
