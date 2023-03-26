package com.theways.inventorynew.ui

import com.theways.inventorynew.data.Item

data class HomeUiState(
    val items: List<Item> = listOf()
)
