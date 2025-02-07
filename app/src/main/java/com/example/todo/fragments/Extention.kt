package com.example.todo.fragments

import java.util.Calendar

fun Calendar.setDate(year : Int , month : Int , day : Int){
    set(Calendar.YEAR,year)
    set(Calendar.MONTH,month)
    set(Calendar.DAY_OF_MONTH,day)
}

fun Calendar.clearTime(){
    set(Calendar.HOUR,0)
    set(Calendar.MINUTE,0)
    set(Calendar.SECOND,0)
    set(Calendar.MILLISECOND,0)
}