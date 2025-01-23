package com.example.projectpam.ui.view.viewtiket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.example.projectpam.ui.viewmodel.viewmodeltiket.UpdateTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.toTiket
import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "$route/{$ID_TIKET}"
    override val titleRes = "Update Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketView (
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
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){ EntryBody(insertUiState = uiState,
            onTiketValueChange  = { updateValue->
                viewModel.updateTiketState(updateValue)
            },
            onSaveClick = { uiState.insertUiEvent?.let { insertUiEvent ->
                coroutineScope.launch {
                    viewModel.updateTiket(
                        id_tiket = viewModel.id_tiket,
                        tiket = insertUiEvent.toTiket()
                    )
                    navigateBack()
                }
            }
            }
        )
        }
    }
}