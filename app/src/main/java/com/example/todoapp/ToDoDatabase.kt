package com.example.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ToDoItem::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getDao() : ToDoDao

   companion object{
       @Volatile
       private var INSTANCE: ToDoDatabase? = null

       fun getDatabase(context: Context): ToDoDatabase {
           // if the INSTANCE is not null, then return it,
           // if it is, then create the database
           return INSTANCE ?: synchronized(this) {
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   ToDoDatabase::class.java,
                   "word_database"
               ).build()
               INSTANCE = instance
               // return instance
               instance
           }
       }
   }
}