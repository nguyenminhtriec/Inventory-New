package com.theways.inventorynew.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.theways.inventorynew.InventoryApplication
import com.theways.inventorynew.data.Item
import java.text.NumberFormat

fun ItemUiState.toItem(): Item {
    return Item(
        id = id,
        name = name,
        price = price.toDoubleOrNull() ?: 0.0,
        quantity = quantity.toIntOrNull() ?: 0
    )
}

fun Item.toItemUiState(actionEnabled: Boolean): ItemUiState {
    return ItemUiState(
        id = this.id,
        name = this.name,
        price = this.price.toString(),
        quantity = this.quantity.toString(),
        actionEnabled = actionEnabled
    )
}

fun ItemUiState.isValid(): Boolean {
    return this.name.isNotBlank() && this.price.isNotBlank() && this.quantity.isNotBlank()
}

fun CreationExtras.inventoryApplication(): InventoryApplication {
    return this[APPLICATION_KEY] as InventoryApplication
}

fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}