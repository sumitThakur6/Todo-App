package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TodoAdapter.OnItemClick {
    private lateinit var mAdapter : TodoAdapter
    private lateinit var viewModel : ToDoViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = TodoAdapter( this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter

        viewModel = ViewModelProvider(this,
                 ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ToDoViewModel::class.java]
        viewModel.tasks.observe(this, Observer {
            mAdapter.submitList(it)
        })

        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack(mAdapter, viewModel))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onCheckBoxClick(task: ToDoItem, isChecked: Boolean) {
        viewModel.updateTask(task, isChecked)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.dltMenu -> {
                showDeleteConfirmationDialog()
            }
        }
       return super.onOptionsItemSelected(item)
    }
    private fun showDeleteConfirmationDialog(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Delete Task")
        alertDialog.setMessage("Are you sure you want to delete the entries")
        alertDialog.setPositiveButton("Yes"){_, _ ->
            viewModel.deleteAllEntries()
            Toast.makeText(this, "All Tasks Deleted", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.create().show()
    }

    fun showDialog(view: View) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input_layout, null)
        val etTask = dialogView.findViewById<EditText>(R.id.etTaskInput)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setTitle("Input")
            .setPositiveButton("Submit") { _, _ ->
                val input = etTask.text.toString()
                if(input.isNotEmpty()) {
                    viewModel.insertTask(ToDoItem(task = input.capitalized()))
                    Toast.makeText(this, "Task Inserted", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Please add your task", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }
        dialogBuilder.create().show()
    }
    private fun String.capitalized(): String {
    val words = split(" ")
    val capitalizedWords = words.joinToString(" ") {
        it.lowercase(Locale.getDefault()).replaceFirstChar { char ->
               char.uppercase(Locale.getDefault())
        }
    }
    return capitalizedWords
    }

}

