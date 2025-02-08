package com.example.todo.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var date: Date? = null,
    var isDone: Boolean
) : Parcelable {
    @Ignore
    var description : String? = null
}
