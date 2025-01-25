package com.example.projectpam.ui.view.viewpeserta

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
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.UpdatePesertaViewModel
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.toPeserta
import kotlinx.coroutines.launch

object DestinasiUpdatePeserta : DestinasiNavigasi {
    override val route = "updatePeserta"
    const val ID_PESERTA = "id_peserta"
    val routesWithArg = "$route/{$ID_PESERTA}"
    override val titleRes = "Update Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePesertaView (
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState.value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePeserta.titleRes,
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
        ){ EntryBodyPeserta(insertUiState = uiState,
            onPesertaValueChange  = { updateValue->
                viewModel.updatePesertaState(updateValue)
            },
            onSaveClick = { uiState.insertUiEvent?.let { insertUiEvent ->
                coroutineScope.launch {
                    viewModel.updatePeserta(
                        id_peserta = viewModel.id_peserta,
                        peserta = insertUiEvent.toPeserta()
                    )
                    navigateBack()
                }
            }
            }
        )
        }
    }
}