package com.example.projectpam.ui.view.viewpeserta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertPesertaUiEvent
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertPesertaUiState
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertPesertaViewModel
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPeserta : DestinasiNavigasi {
    override val route = "item_entry_peserta"
    override val titleRes = "Insert Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPesertaScreen (
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPeserta.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyPeserta(
            insertUiState = viewModel.uiState,
            onPesertaValueChange = viewModel::updateInsertPesertaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPeserta()
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
fun EntryBodyPeserta (
    insertUiState: InsertPesertaUiState,
    onPesertaValueChange: (InsertPesertaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPeserta(
            insertPesertaUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPesertaValueChange,
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
fun FormInputPeserta(
    insertPesertaUiEvent: InsertPesertaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPesertaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField (
            value = insertPesertaUiEvent.id_peserta,
            onValueChange = {onValueChange(
                insertPesertaUiEvent.copy(id_peserta = it)
            )},
            label = { Text("ID Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertPesertaUiEvent.nama_peserta,
            onValueChange = {onValueChange(insertPesertaUiEvent.copy(nama_peserta = it))},
            label = { Text("Nama Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertPesertaUiEvent.email,
            onValueChange = {onValueChange(insertPesertaUiEvent.copy(email = it))},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField (
            value = insertPesertaUiEvent.nomor_telepon,
            onValueChange = {onValueChange(insertPesertaUiEvent.copy(nomor_telepon = it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Nomor telepon") },
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