package com.theways.inventorynew.data

interface Repository {

    suspend fun insertItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)

     suspend fun getItem(id: Int): Item
    suspend fun getAllItems(): List<Item>
}

class DataRepository(private val itemDao: ItemDao) : Repository {

    override suspend fun insertItem(item: Item) = itemDao.insertItem(item)
    override suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)
    override suspend fun updateItem(item: Item) = itemDao.updateItem(item)

    override suspend fun getItem(id: Int): Item = itemDao.getItem(id)
    override suspend fun getAllItems(): List<Item> = itemDao.getAllItems()
}