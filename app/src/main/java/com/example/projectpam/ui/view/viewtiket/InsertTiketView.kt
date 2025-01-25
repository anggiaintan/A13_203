package com.example.projectpam.ui.view.viewtiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketUiEvent
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketUiState
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "item_entry_tiket"
    override val titleRes = "Insert Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen (
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyTiket(
            insertUiState = viewModel.uiState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            }, modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyTiket (
    insertUiState: InsertTiketUiState,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTiket(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTiketValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTiket(
    insertUiEvent: InsertTiketUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTiketUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField (
            value = insertUiEvent.id_tiket,
            onValueChange = {onValueChange(
                insertUiEvent.copy(id_tiket = it)
            )},
            label = { Text("ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertUiEvent.id_event,
            onValueChange = {onValueChange(insertUiEvent.copy(id_event = it))},
            label = { Text("ID Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertUiEvent.id_pengguna,
            onValueChange = {onValueChange(insertUiEvent.copy(id_pengguna = it))},
            label = { Text("ID Pengguna") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertUiEvent.kapasitas_tiket.toString(),
            onValueChange = {onValueChange(insertUiEvent.copy(kapasitas_tiket = it.toIntOrNull() ?: 0))},
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertUiEvent.harga_tiket,
            onValueChange = {onValueChange(insertUiEvent.copy(harga_tiket = it))},
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi semua data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}