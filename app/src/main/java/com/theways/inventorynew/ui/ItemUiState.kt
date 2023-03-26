package com.theways.inventorynew.ui

import com.theways.inventorynew.InventoryApplication
import com.theways.inventorynew.data.Item

data class ItemUiState(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val actionEnabled: Boolean = false,
)
