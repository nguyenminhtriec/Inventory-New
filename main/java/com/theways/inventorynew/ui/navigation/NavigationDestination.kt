package com.theways.inventorynew.ui.navigation

import com.theways.inventorynew.R

interface NavigationDestination {
    val route: String
    val titleRes: Int

    object HomeDestination : NavigationDestination {
        override val route: String = "Inventory New"
        override val titleRes: Int = R.string.app_name
    }
    object ItemEntryDestination : NavigationDestination {
        override val route: String = "Item Entry"
        override val titleRes: Int = R.string.item_entry_title
    }
    object ItemEditDestination : NavigationDestination {
        override val route: String = "Item Details"
        override val titleRes: Int = R.string.edit_item_title
    }
}