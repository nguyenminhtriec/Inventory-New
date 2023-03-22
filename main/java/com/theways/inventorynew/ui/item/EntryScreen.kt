package com.theways.inventorynew.ui.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.theways.inventorynew.InventoryTopAppBar
import com.theways.inventorynew.R
import com.theways.inventorynew.ui.InventoryViewModel
import com.theways.inventorynew.ui.ItemUiState
import com.theways.inventorynew.ui.isValid
import com.theways.inventorynew.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

@Composable
fun ItemEntryScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel // = viewModel(factory = InventoryViewModel.Factory)

) {
    Scaffold(
        modifier = modifier,
        topBar = {
            InventoryTopAppBar(
                title = stringResource(NavigationDestination.ItemEntryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                modifier = modifier,
            )
        }
    ) {
        val coroutineScope = rememberCoroutineScope()

        ItemEntryBody(
            itemUiState = viewModel.itemUiState,
            onItemUiStateChange = { viewModel.updateItemUiState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertItem()
                    viewModel.updateItemUiState(ItemUiState())
                    viewModel.updateHomeUiState()
                } },
            onCancelClick = { viewModel.updateItemUiState(ItemUiState()) },
            modifier = modifier.padding(it)
        )
    }
}


@Composable
fun ItemEntryBody(
    itemUiState: ItemUiState,
    onItemUiStateChange: (ItemUiState) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        EntryForm(
            itemUiState = itemUiState,
            onItemUiStateChange = onItemUiStateChange,
            //modifier = modifier,
        )
        ActionButton(
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
            enabledSave = itemUiState.isValid(),
            //modifier = modifier,
        )
    }
}

@Composable
fun EntryForm(
    itemUiState: ItemUiState,
    onItemUiStateChange: (ItemUiState) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = itemUiState.name, 
            onValueChange = { onItemUiStateChange(itemUiState.copy(name = it)) },
            label = { Text(stringResource(R.string.item_name_req)) },
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = itemUiState.price, 
            onValueChange = { onItemUiStateChange(itemUiState.copy(price = it)) },
            label = { Text(stringResource(R.string.item_price_req)) },
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = itemUiState.quantity, 
            onValueChange = { onItemUiStateChange(itemUiState.copy(quantity = it)) },
            label = { Text(stringResource(R.string.quantity_in_stock)) },
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )
    }
}

@Composable
private fun ActionButton(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    enabledSave: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        Button(onClick = onCancelClick) {
            Text(stringResource(R.string.cancel).uppercase())
        }
        Button(onClick = onSaveClick, enabled = enabledSave) {
            Text(stringResource(R.string.save_action).uppercase())
        }
    }
}