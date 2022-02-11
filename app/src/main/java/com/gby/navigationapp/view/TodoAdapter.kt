package com.gby.navigationapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gby.navigationapp.R
import com.gby.navigationapp.model.TodoItem
import com.gby.navigationapp.view.fragments.ListTodoFragmentDirections
import com.gby.navigationapp.viewmodel.StateManager

class TodoAdapter(private val context: Context, private var model: StateManager, var data: ArrayList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView = itemView.findViewById<View>(R.id.item_divider)
        val titleTextView = itemView.findViewById<TextView>(R.id.item_title)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.item_description)
        val timeTextView = itemView.findViewById<TextView>(R.id.item_time)
        val deleteBtn = itemView.findViewById<ImageView>(R.id.item_delete)
        val editBtn = itemView.findViewById<ImageView>(R.id.item_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
    )


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            colorView.setBackgroundColor(data[position].color)
            titleTextView.text = data[position].title
            descriptionTextView.text = data[position].description
            timeTextView.text = data[position].date

            deleteBtn.setOnClickListener {
                notifyDataSetChanged()
                model.deleteTodoItem(data[position].id)
            }
            editBtn.setOnClickListener {
                val action = ListTodoFragmentDirections.actionHome4ToAddTodoFragment(data[position].id)
                it.findNavController().navigate(action)
            }

        }
    }

    override fun getItemCount(): Int = data.size
}