package com.example.todo.viewContanier

import android.view.View
import android.widget.TextView
import com.example.todo.R
import com.example.todo.databinding.ItemWeekDayBinding
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class CustomWeekDayBinder(val unSelectedColor : Int,val selectedColor : Int,val onDateSelected : (WeekDay) -> Unit) : WeekDayBinder<DayViewContainer>{
    var selectedDate: LocalDate? = null
    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.weekDayNameText.text = data.date.dayOfWeek.getDisplayName(TextStyle.SHORT,Locale.getDefault())
        container.monthDayNameText.text = "${data.date.dayOfMonth}"
        if (selectedDate == data.date){
            container.weekDayNameText.setTextColor(selectedColor)
            container.monthDayNameText.setTextColor(selectedColor)
        }else{
            container.weekDayNameText.setTextColor(unSelectedColor)
            container.monthDayNameText.setTextColor(unSelectedColor)
        }
        container.view.setOnClickListener {
            onDateSelected(data)

        }
    }

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view)
    }

}
class DayViewContainer(view: View) : ViewContainer(view) {
    val weekDayNameText = ItemWeekDayBinding.bind(view).weekDayTv
    val monthDayNameText = ItemWeekDayBinding.bind(view).monthDayTv

}