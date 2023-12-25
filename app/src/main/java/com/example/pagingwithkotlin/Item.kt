package com.example.pagingwithkotlin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    // Add other fields as needed
)
