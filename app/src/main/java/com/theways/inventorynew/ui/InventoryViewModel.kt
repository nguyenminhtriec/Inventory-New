package com.theways.inventorynew.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.util.newStringBuilder
import com.theways.inventorynew.InventoryApplication
import com.theways.inventorynew.data.Item
import com.theways.inventorynew.data.Repository
import kotlinx.coroutines.launch

class InventoryViewModel(private val inventoryRepository: Repository) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        updateHomeUiState()
    }

    fun updateItemUiState(newItemUiState: ItemUiState) {
        itemUiState = newItemUiState.copy(actionEnabled = newItemUiState.isValid())
    }

    fun updateHomeUiState() {
        viewModelScope.launch {
            try {
                val updated = inventoryRepository.getAllItems()
                homeUiState = homeUiState.copy(items = updated)
            } catch (e: Exception) {
                Log.e("NWT", e.message ?: "____")
            }
        }
    }

    suspend fun insertItem() {
        if (itemUiState.isValid())
            inventoryRepository.insertItem(itemUiState.toItem())
    }

    suspend fun updateItem() {
        if (itemUiState.isValid())
            inventoryRepository.updateItem(itemUiState.toItem())
    }

    suspend fun deleteItem() {
        inventoryRepository.deleteItem(itemUiState.toItem())
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                //val inventoryApplication = this[APPLICATION_KEY] as InventoryApplication
                //val appContainer = inventoryApplication.appContainer
                //InventoryViewModel(appContainer.inventoryRepository)
                InventoryViewModel(this.inventoryApplication().appContainer.inventoryRepository)
            }
        }
    }
}