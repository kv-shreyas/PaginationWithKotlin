package com.example.pagingwithkotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingwithkotlin.databinding.ItemLayoutBinding

class MyItemAdapter : PagingDataAdapter<Item, MyItemAdapter.ItemViewHolder>(ItemComparator) {

    private val TAG: String= "Kvs"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.i("Kvs", "onCreateViewHolder:")

        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.i("Kvs", "onBindViewHolder: $position")

        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            // Bind your item data to the view here
            // Example: binding.textView.text = item.name

            Log.i("Kvs", "bind: "+item.name)
            binding.text2.text = item.name
        }
    }

    object ItemComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}
