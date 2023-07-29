package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter( private val listener : OnItemClick) : ListAdapter<ToDoItem, TodoAdapter.TodoViewHolder>(DiffCallBack()){

    inner class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private val tvTask: TextView = itemView.findViewById(R.id.tvTask)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

      fun bind(item: ToDoItem) {
            tvTask.text = item.task
            checkBox.isChecked = item.isCompleted
            tvTask.paint.isStrikeThruText = item.isCompleted

          checkBox.setOnClickListener{
              val position = adapterPosition
              if(position != RecyclerView.NO_POSITION){
                  val task = getItem(position)
                  listener.onCheckBoxClick(task, checkBox.isChecked)
              }
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return TodoViewHolder(viewHolder)
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun deleteOnSwipe(position: Int): ToDoItem {
        return currentList[position]
    }

    interface OnItemClick{
        fun onCheckBoxClick(task : ToDoItem, isChecked : Boolean)
    }
}


class DiffCallBack : DiffUtil.ItemCallback<ToDoItem>(){
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return  (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return  (oldItem.id == newItem.id)
    }
}