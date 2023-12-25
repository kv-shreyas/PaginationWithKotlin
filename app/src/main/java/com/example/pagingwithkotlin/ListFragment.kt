package com.example.pagingwithkotlin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.example.pagingwithkotlin.databinding.FragmentListBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {

   private lateinit var binding: FragmentListBinding

    private lateinit var viewModel: ListViewModel
    private val adapter = MyItemAdapter()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        binding.myRecyclerView1.adapter = adapter

        // In your activity
        val sortingOptions = resources.getStringArray(R.array.sorting_options)
        val spinnerAdapter = context?.let {
            ArrayAdapter(
                it.applicationContext,
                android.R.layout.simple_spinner_item,
                sortingOptions
            )
        }

        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.sortSpinner.adapter = spinnerAdapter

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.i("kvs", "onItemSelected: $position")
//                getList(position.toLong())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("kvs", "onNothingSelected: ")
            }

        }

        binding.filterBtn.setOnClickListener {
            if (binding.editText.text.toString().isNotEmpty()) {
                getList(binding.editText.text.toString().toLongOrNull() ?: 0L)
                binding.editText.text.clear()
            }
        }

//        insertList()
        /*lifecycleScope.launch {
            context?.let {
                viewModel.getItems(it.applicationContext).collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                    Log.i("kvs", "submitData: $pagingData")
                }
            }
        }*/

        getList(0)
    }
    private fun getList(filterBy: Long){
        lifecycleScope.launch {
            context?.let {
                viewModel.getFilteredAndSortedItems(it.applicationContext, filterBy)
                    .collectLatest { pagingData ->
                        Log.i("kvs", "submitData: $pagingData")
                        adapter.submitData(pagingData)
                    }
            }
        }
    }
    private fun insertList(){
        lifecycleScope.launch {
            context?.let {

                val arrayList = mutableListOf<Item>()
                for (i in 1..6000) {
                    arrayList.add(Item(name = "item $i"))
                    Log.i("kvs", "inserted item into db: $i")
                }
                viewModel.insertItems(requireContext().applicationContext,arrayList)

            }
        }
    }

}