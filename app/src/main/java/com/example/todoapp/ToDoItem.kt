package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoItem (
    val task : String,
    var isCompleted : Boolean = false
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}