package com.theways.inventorynew.ui.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.theways.inventorynew.ui.InventoryViewModel
import com.theways.inventorynew.ui.navigation.NavigationDestination
import com.theways.inventorynew.ui.toItemUiState

@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel = viewModel(factory = InventoryViewModel.Factory),
) {

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.HomeDestination.route
    ) {
        composable(route = NavigationDestination.HomeDestination.route) {
            HomeScreen(
                modifier = modifier,
                navigateToItemEntry = {
                    navController.navigate(NavigationDestination.ItemEntryDestination.route)
                },
                onItemClick = {
                    viewModel.updateItemUiState(it.toItemUiState(false))
                    navController.navigate(NavigationDestination.ItemEditDestination.route)
                },
                viewModel = viewModel
            )
        }
        composable(route = NavigationDestination.ItemEditDestination.route) {
            ItemEditScreen(
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )

        }
        composable(route = NavigationDestination.ItemEntryDestination.route) {
            ItemEntryScreen(
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}