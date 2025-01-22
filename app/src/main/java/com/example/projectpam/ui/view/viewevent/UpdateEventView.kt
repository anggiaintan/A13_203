package com.example.projectpam.ui.view.viewevent

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
import com.example.projectpam.ui.viewmodel.viewmodelevent.UpdateEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodelevent.toEvent
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    const val ID_EVENT = "id_event"
    val routesWithArg = "$route/{$ID_EVENT}"
    override val titleRes = "Update Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEventView (
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
        ){ com.example.projectpam.ui.view.viewevent.EntryBody(insertUiState = uiState,
            onEventValueChange  = { updateValue->
                viewModel.updateEventState(updateValue)
            },
            onSaveClick = { uiState.insertUiEvent?.let { insertUiEvent ->
                coroutineScope.launch {
                    viewModel.updateEvent(
                        id_event = viewModel.id_event,
                        event = insertUiEvent.toEvent()
                    )
                    navigateBack()
                }
            }
            }
        )
        }
    }
}