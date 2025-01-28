package com.example.projectpam.ui.view.viewtiket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketUiEvent
import com.example.projectpam.ui.viewmodel.viewmodeltiket.UpdateTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.toTiket
import kotlinx.coroutines.launch

object DestinasiUpdateTiket : DestinasiNavigasi {
    override val route = "updateTiket"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "$route/{$ID_TIKET}"
    override val titleRes = "Update Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            uiState?.insertUiEvent?.let { insertUiEvent ->
                FormInputUpdateTiket(
                    insertUiEvent = insertUiEvent,
                    onValueChange = viewModel::updateTiketState,
                    eventList = viewModel.eventList,
                    pesertaList = viewModel.pesertaList,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateTiket(
                                id_tiket = viewModel.id_tiket,
                                tiket = insertUiEvent.toTiket()
                            )
                            navigateBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Tiket")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputUpdateTiket(
    insertUiEvent: InsertTiketUiEvent,
    onValueChange: (InsertTiketUiEvent) -> Unit,
    eventList: List<String>,
    pesertaList: List<String>,
    modifier: Modifier = Modifier
) {
    var expandedEvent by remember { mutableStateOf(false) }
    var expandedPeserta by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ID Tiket (Read-only in update mode)
        OutlinedTextField(
            value = insertUiEvent.id_tiket,
            onValueChange = { },
            label = { Text("ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )

        // Event Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedEvent,
            onExpandedChange = { expandedEvent = it }
        ) {
            OutlinedTextField(
                value = insertUiEvent.id_event,
                onValueChange = { },
                readOnly = true,
                label = { Text("Event") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEvent) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedEvent,
                onDismissRequest = { expandedEvent = false }
            ) {
                eventList.forEach { event ->
                    DropdownMenuItem(
                        text = { Text(event) },
                        onClick = {
                            onValueChange(insertUiEvent.copy(id_event = event))
                            expandedEvent = false
                        }
                    )
                }
            }
        }

        // Peserta Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedPeserta,
            onExpandedChange = { expandedPeserta = it }
        ) {
            OutlinedTextField(
                value = insertUiEvent.id_peserta,
                onValueChange = { },
                readOnly = true,
                label = { Text("Peserta") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeserta) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedPeserta,
                onDismissRequest = { expandedPeserta = false }
            ) {
                pesertaList.forEach { peserta ->
                    DropdownMenuItem(
                        text = { Text(peserta) },
                        onClick = {
                            onValueChange(insertUiEvent.copy(id_peserta = peserta))
                            expandedPeserta = false
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
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Harga Tiket
        OutlinedTextField(
            value = insertUiEvent.harga_tiket,
            onValueChange = { onValueChange(insertUiEvent.copy(harga_tiket = it)) },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}