package com.theways.inventorynew.data

import android.content.Context

interface Container {
    val inventoryRepository: Repository
}

class AppContainer(private val context: Context) : Container {
    override val inventoryRepository: Repository by lazy {
        DataRepository(InventoryDatabase.getDatabase(context).getItemDao())
    }
}