package com.example.pagingwithkotlin

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItems(): PagingSource<Int, Item>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Item>): List<Long>
    // Add other queries as needed
}