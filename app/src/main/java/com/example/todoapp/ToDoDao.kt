package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task : ToDoItem)

    @Update
    suspend fun updateTask(task : ToDoItem)

    @Delete
    suspend fun deleteTask(task : ToDoItem)

    @Query("DELETE from todo_table")
    suspend fun deleteAllEntries()

    @Query("Select * from todo_table")
    fun getTasks() : LiveData<List<ToDoItem>>
}