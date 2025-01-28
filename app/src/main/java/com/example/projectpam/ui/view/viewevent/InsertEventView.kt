package com.example.projectpam.ui.view.viewevent

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
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertEventUiEvent
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertEventUiState
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryEvent : DestinasiNavigasi {
    override val route = "item_entry_event"
    override val titleRes = "Insert Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen (
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyEvent(
            insertUiState = viewModel.uiState,
            onEventValueChange = viewModel::updateInsertEvnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertEvn()
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
fun EntryBodyEvent (
    insertUiState: InsertEventUiState,
    onEventValueChange: (InsertEventUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputEvent(
            insertEventUiEvent = insertUiState.insertUiEvent,
            onValueChange = onEventValueChange,
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
fun FormInputEvent(
    insertEventUiEvent: InsertEventUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertEventUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField (
            value = insertEventUiEvent.id_event,
            onValueChange = {onValueChange(
                insertEventUiEvent.copy(id_event = it)
            )},
            label = { Text("ID Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertEventUiEvent.nama_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(nama_event = it))},
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertEventUiEvent.deskripsi_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(deskripsi_event = it))},
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertEventUiEvent.tanggal_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(tanggal_event = it))},
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertEventUiEvent.lokasi_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(lokasi_event = it))},
            label = { Text("Lokasi Event") },
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