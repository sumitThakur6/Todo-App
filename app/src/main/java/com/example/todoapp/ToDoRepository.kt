package com.example.todoapp

import androidx.lifecycle.LiveData

class ToDoRepository(private val dao : ToDoDao) {

    val tasks : LiveData<List<ToDoItem>> = dao.getTasks()

    suspend fun insertTask(task : ToDoItem){
        dao.insertTask(task)
    }

    suspend fun updateTask(task : ToDoItem){
        dao.updateTask(task)
    }

    suspend fun deleteAllEntries(){
        dao.deleteAllEntries()
    }

    suspend fun deleteTask(task : ToDoItem){
        dao.deleteTask(task)
    }
}