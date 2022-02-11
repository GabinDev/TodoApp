package com.gby.navigationapp.view.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gby.navigationapp.R
import com.gby.navigationapp.databinding.ListTodoFragmentBinding
import com.gby.navigationapp.model.TodoItem
import com.gby.navigationapp.view.TodoAdapter
import com.gby.navigationapp.viewmodel.StateManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ListTodoFragment : Fragment() {

    private lateinit var binding: ListTodoFragmentBinding
    private val model: StateManager by activityViewModels()
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListTodoFragmentBinding.inflate(layoutInflater)
        model.todoItem.observe(viewLifecycleOwner){
            initAdapter(it!!)
            binding.ll1.visibility = if(it!!.size >0)View.VISIBLE else View.GONE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addTodo.setOnClickListener{
                val action = ListTodoFragmentDirections.actionHome4ToAddTodoFragment()
                it.findNavController().navigate(action)
            }
            deleteAll.setOnClickListener{
                dialogueDelete()
            }
        }
    }

    private fun initAdapter(data : ArrayList<TodoItem>){
        adapter  = TodoAdapter(requireContext(), model, data )
        binding.apply {
            recycleView.setHasFixedSize(true)
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            recycleView.adapter = adapter
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun dialogueDelete(){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.text_delete_all))
            .setNegativeButton(getString(R.string.text_cancel)) {_, _ ->}
            .setPositiveButton(getString(R.string.text_delete)) { _, _ ->
                adapter.notifyDataSetChanged()
                model.deleteAllTodoItem()
                binding.ll1.visibility = View.GONE
            }
            .show()
    }


}