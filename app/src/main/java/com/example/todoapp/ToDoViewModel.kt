package com.example.todoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    val tasks : LiveData<List<ToDoItem>>
    private val repository : ToDoRepository
    init {
        val dao = ToDoDatabase.getDatabase(application).getDao()
        repository = ToDoRepository(dao)
        tasks = repository.tasks
    }

    fun insertTask(task : ToDoItem) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(task)
    }

    fun updateTask(task : ToDoItem, isChecked : Boolean) = viewModelScope.launch {
        task.isCompleted = isChecked
        repository.updateTask(task)
        Log.d("Msg", task.toString())
    }

    fun deleteAllEntries() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllEntries()
    }

    fun deleteTask(task : ToDoItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTask(task)
    }

}