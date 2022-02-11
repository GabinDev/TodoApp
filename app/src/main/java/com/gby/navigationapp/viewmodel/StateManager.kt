package com.gby.navigationapp.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gby.navigationapp.model.TodoItem

class StateManager : ViewModel() {
    private val _todoItem = MutableLiveData(
        arrayListOf(
            TodoItem(1, "Title 1", "Description 1", "10-11-2022", Color.GRAY)
        )
    )
    val todoItem: LiveData<ArrayList<TodoItem>> = _todoItem

    fun setTodoItem(todoItem: TodoItem) {
        _todoItem.value!!.add(todoItem)
    }

    fun updateTodoItem(todoItem: TodoItem, idTodo: Int) {
        _todoItem.value!!.forEach { t ->
            if (t.id == idTodo) {
                t.title = todoItem.title
                t.description = todoItem.description
                t.date = todoItem.date
            }
        }
    }

    fun deleteTodoItem(idTodo: Int) {
        val newList  = _todoItem.value!!.filter { t->t.id != idTodo }
        _todoItem.value!!.clear()
        _todoItem.value!!.addAll(newList)
    }

    fun deleteAllTodoItem() {
        _todoItem.value!!.clear()
    }

}