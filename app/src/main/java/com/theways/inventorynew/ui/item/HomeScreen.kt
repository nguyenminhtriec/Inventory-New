package com.theways.inventorynew.ui.item

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.theways.inventorynew.InventoryTopAppBar
import com.theways.inventorynew.ui.HomeUiState
import com.theways.inventorynew.ui.InventoryViewModel
import com.theways.inventorynew.data.Item
import com.theways.inventorynew.ui.navigation.NavigationDestination
import com.theways.inventorynew.ui.toCurrency
import com.theways.inventorynew.ui.toItemUiState
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    onItemClick: (Item) -> Unit, // navigate to ItemEditScreen with an argument: (Item)
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel // = viewModel(factory = InventoryViewModel.Factory)
) {
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            InventoryTopAppBar(
                title = stringResource(NavigationDestination.HomeDestination.titleRes),
                canNavigateBack = false,
                navigateUp = {},
                modifier = modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemEntry, shape = RoundedCornerShape(6.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        HomeBody(
            homeUiState = viewModel.homeUiState.collectAsState().value,
            onItemClick = onItemClick,
            modifier = modifier.padding(it),
        )
    }
}

@Composable
fun HomeBody(
    homeUiState: HomeUiState,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier,
) {
    ItemListColumn(
        itemList = homeUiState.items,
        onItemClick = onItemClick,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
private fun ItemListColumn(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        HeaderRow()

        Divider()

        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(items = itemList, key = { item: Item -> item.id }) {
                ItemRow(
                    item = it,
                    onItemClick = onItemClick
                )
            }
        }
    }

}

@Composable
private fun HeaderRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("##", modifier = modifier.weight(1f))
        Text("Name", modifier = modifier.weight(4f))
        Text("Price", modifier = modifier.weight(2f), textAlign = TextAlign.Right)
        Text("Quantity", modifier = modifier.weight(2f), textAlign = TextAlign.Right)
    }

}

@Composable
private fun ItemRow(
    item: Item,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = { onItemClick(item) })
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(item.id.toString(), modifier = modifier.weight(1f))
        Text(item.name, modifier = modifier.weight(4f))
        Text(item.price.toCurrency(),
            modifier = modifier.weight(2f),
            textAlign = TextAlign.Right)
        Text(item.quantity.toString(),
            modifier = modifier.weight(2f),
            textAlign = TextAlign.Right)
    }
}