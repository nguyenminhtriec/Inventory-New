package com.theways.inventorynew.ui.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.theways.inventorynew.InventoryTopAppBar
import com.theways.inventorynew.R
import com.theways.inventorynew.ui.InventoryViewModel
import com.theways.inventorynew.ui.ItemUiState
import com.theways.inventorynew.ui.isValid
import com.theways.inventorynew.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

@Composable
fun ItemEditScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel // InventoryViewModel = viewModel(factory = InventoryViewModel.Factory)
) {
    var isShowingDialogue by rememberSaveable { mutableStateOf(false) }
    var editEnabled by rememberSaveable { mutableStateOf(false)

    }

    Scaffold(
        modifier = modifier,
        topBar = {
            InventoryTopAppBar(
                title = stringResource(NavigationDestination.ItemEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                modifier = modifier,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { editEnabled = !editEnabled }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    ) {
        val coroutineScope = rememberCoroutineScope()

        ItemEditBody(
            itemUiState = viewModel.itemUiState,
            onItemUiStateChange = { viewModel.updateItemUiState(it) } ,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
                    viewModel.updateItemUiState(ItemUiState())
                    //viewModel.updateHomeUiState(), HomeUIState automatically updated by itself
                } },
            onCancelClick = { viewModel.updateItemUiState(ItemUiState()) },
            onDeleteClick = { isShowingDialogue = true },
            modifier = modifier.padding(it),
            editEnabled = editEnabled
        )
        if (isShowingDialogue) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    isShowingDialogue = false
                    coroutineScope.launch {
                        viewModel.deleteItem()
                        viewModel.updateItemUiState(ItemUiState())
                        //viewModel.updateHomeUiState(), HomeUIState automatically updated by itself
                    }
                },
                onDeleteCancel = { isShowingDialogue = false },
            )
        }
    }
}

@Composable
fun ItemEditBody(
    itemUiState: ItemUiState,
    onItemUiStateChange: (ItemUiState) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    editEnabled: Boolean = false
) {
    Column(modifier = modifier.fillMaxSize()) {
        EntryForm(
            itemUiState = itemUiState,
            onItemUiStateChange = onItemUiStateChange,
            enabled = editEnabled
        )
        ActionButtons(
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick,
            onDeleteClick = onDeleteClick,
            enabledSave = itemUiState.isValid() && itemUiState.actionEnabled && editEnabled,
        )
    }
}

@Composable
private fun ActionButtons(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    enabledSave: Boolean,
    modifier: Modifier = Modifier
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
        Button(onClick = onDeleteClick) {
            Text(stringResource(R.string.delete).uppercase())
        }
        Button(onClick = onSaveClick, enabled = enabledSave) {
            Text(stringResource(R.string.save_action).uppercase())
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}
