package com.example.projectpam.ui.view.viewtiket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketUiState
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketUiEvent
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel

object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "item_entry_tiket"
    override val titleRes = "Insert Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyTiket(
            insertUiState = viewModel.uiState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            },
            eventList = viewModel.eventList,
            pesertaList = viewModel.pesertaList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyTiket(
    insertUiState: InsertTiketUiState,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    eventList: List<String>,
    pesertaList: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTiket(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTiketValueChange,
            eventList = eventList,
            pesertaList = pesertaList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = insertUiState.insertUiEvent.isValid(),
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
    onValueChange: (InsertTiketUiEvent) -> Unit,
    enabled: Boolean = true,
    eventList: List<String>,
    pesertaList: List<String>,
    modifier: Modifier = Modifier
) {
    var isEventExpanded by remember { mutableStateOf(false) }
    var isPesertaExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ID Tiket
        OutlinedTextField(
            value = insertUiEvent.id_tiket,
            onValueChange = { onValueChange(insertUiEvent.copy(id_tiket = it)) },
            label = { Text("ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Event Dropdown
        ExposedDropdownMenuBox(
            expanded = isEventExpanded,
            onExpandedChange = { isEventExpanded = !isEventExpanded },
        ) {
            OutlinedTextField(
                value = insertUiEvent.id_event,
                onValueChange = {},
                readOnly = true,
                label = { Text("Pilih ID Event") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEventExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = enabled
            )

            ExposedDropdownMenu(
                expanded = isEventExpanded,
                onDismissRequest = { isEventExpanded = false }
            ) {
                eventList.forEach { event ->
                    DropdownMenuItem(
                        text = { Text(event) },
                        onClick = {
                            onValueChange(insertUiEvent.copy(id_event = event))
                            isEventExpanded = false
                        }
                    )
                }
            }
        }

        // Peserta Dropdown
        ExposedDropdownMenuBox(
            expanded = isPesertaExpanded,
            onExpandedChange = { isPesertaExpanded = !isPesertaExpanded },
        ) {
            OutlinedTextField(
                value = insertUiEvent.id_peserta,
                onValueChange = {},
                readOnly = true,
                label = { Text("Pilih ID Peserta") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPesertaExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                enabled = enabled
            )

            ExposedDropdownMenu(
                expanded = isPesertaExpanded,
                onDismissRequest = { isPesertaExpanded = false }
            ) {
                pesertaList.forEach { peserta ->
                    DropdownMenuItem(
                        text = { Text(peserta) },
                        onClick = {
                            onValueChange(insertUiEvent.copy(id_peserta = peserta))
                            isPesertaExpanded = false
                        }
                    )
                }
            }
        }

        // Kapasitas Tiket
        OutlinedTextField(
            value = insertUiEvent.kapasitas_tiket.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0
                onValueChange(insertUiEvent.copy(kapasitas_tiket = newValue))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Harga Tiket
        OutlinedTextField(
            value = insertUiEvent.harga_tiket,
            onValueChange = { onValueChange(insertUiEvent.copy(harga_tiket = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi semua data!",
                modifier = Modifier.padding(start = 12.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Extension function to validate form
fun InsertTiketUiEvent.isValid(): Boolean {
    return id_tiket.isNotBlank() &&
            id_event.isNotBlank() &&
            id_peserta.isNotBlank() &&
            kapasitas_tiket > 0 &&
            harga_tiket.isNotBlank()
}