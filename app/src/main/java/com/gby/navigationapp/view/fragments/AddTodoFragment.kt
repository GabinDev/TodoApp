package com.gby.navigationapp.view.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.gby.navigationapp.databinding.AddTodoFragmentBinding
import com.gby.navigationapp.model.TodoItem
import com.gby.navigationapp.viewmodel.StateManager
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class AddTodoFragment : Fragment() {
    private lateinit var binding: AddTodoFragmentBinding
    private val args: ListTodoFragmentArgs by navArgs()
    private val model: StateManager by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTodoFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFields()
        binding.apply {
            btnBack.setOnClickListener {
                val action = AddTodoFragmentDirections.actionAddTodoFragmentToHome4()
                it.findNavController().navigate(action)
            }
            etDate.setEndIconOnClickListener {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
                picker.show(parentFragmentManager, "tag")
                picker.addOnPositiveButtonClickListener { d ->
                    val date: String = SimpleDateFormat("dd-MM-yyyy").format(Date(d))
                    binding.etDate.editText!!.setText(date)
                }
            }

            addTodo.setOnClickListener {
                if (
                    etTitle.editText!!.text.toString() !== "" &&
                    etDescription.editText!!.text.toString() !== "" &&
                    etDate.editText!!.text.toString() !== ""
                ) {
                    val todoItem  =TodoItem(
                        model.todoItem.value!!.size,
                        etTitle.editText!!.text.toString(),
                        etDescription.editText!!.text.toString(),
                        etDate.editText!!.text.toString(),
                        randomColor()
                    )
                    if(args.idTodo == 0){
                        model.setTodoItem(todoItem )
                    }else{
                        model.updateTodoItem(todoItem, args.idTodo)
                    }

                }
                val action = AddTodoFragmentDirections.actionAddTodoFragmentToHome4()
                it.findNavController().navigate(action)
            }
        }
    }

    private fun updateFields(){
        if(args.idTodo != 0){
            val todoItem = model.todoItem.value!!.filter { t-> t.id ==args.idTodo }[0]
            binding.apply {
                etTitle.editText!!.setText(todoItem.title)
                etDescription.editText!!.setText(todoItem.description)
                etDate.editText!!.setText(todoItem.date)
            }
        }
    }

    private fun randomColor() : Int{
        val colors = arrayOf(
            Color.BLACK,
            Color.BLUE,
            Color.GRAY,
            Color.YELLOW,
            Color.RED,
            Color.CYAN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.TRANSPARENT,
            Color.GREEN
        )
        val index = (Math.random() * (colors.size - 1)).roundToInt()
        return colors[index]

    }

}