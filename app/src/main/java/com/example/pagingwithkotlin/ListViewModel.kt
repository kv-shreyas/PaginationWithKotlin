package com.example.pagingwithkotlin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class ListViewModel : ViewModel() {

    init {

    }

    suspend fun insertItems(context: Context, list: List<Item>): List<Long> {
        return AppDatabase.getDatabase(context).itemDao().insert(list)
    }

    fun getItems(context: Context): Flow<PagingData<Item>> {
        return getItemsWithConfig(context).flow.cachedIn(viewModelScope)
    }




    // Function to get filtered and sorted data
    fun getFilteredAndSortedItems(context: Context, long: Long): Flow<PagingData<Item>> {
        return getItems(context = context).map { m ->
            m.filter { data ->
                if (long != 0L) {
                    data.id % long == 0L
                } else {
                    true
                }
            }
        }.cachedIn(viewModelScope)
    }
 /*   suspend fun getFilteredAndSortedItems2(context: Context, long: Long): Flow<PagingData<Item>> {
        return getItems(context = context)
            .map { pagingData ->
                pagingData
                    .filter { item ->
                        // Filter condition: keep items with id divisible by 'long'
                        if (long != 0L) {
                            item.id % long == 0L
                        } else {
                            // Handle the case when 'long' is 0, you might want to adjust this condition
                            // For example, keep all items when 'long' is 0
                            true
                        }
                    }
            }.cachedIn(viewModelScope).map { it-> it.map { it.copy() } }.toList().sortedByDescending { it.map { it.id }}.let { PagingData.from(it) }

    }*/

    private fun filterAndSortItems(items: List<Item>): List<Item> {
        // Filter items with even IDs
        val filteredItems = items.filter { it.id % 2 == 0L }

        // Sort items in descending order based on ID
        return filteredItems.sortedByDescending { it.id }
    }


    private fun getItemsWithConfig(context: Context): Pager<Int, Item> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { AppDatabase.getDatabase(context).itemDao().getAllItems() }
        )
    }


}

