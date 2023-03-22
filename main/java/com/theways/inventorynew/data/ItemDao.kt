package com.theways.inventorynew.data

import androidx.room.*

@Dao
interface ItemDao {

    @Insert
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItem(id: Int): Item

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>
}