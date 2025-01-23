package com.example.projectpam.ui.view.viewevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectpam.model.Event
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelevent.DetailEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodelevent.DetailUiState
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel

object DestinasiDetailEvent : DestinasiNavigasi {
    override val route = "detail"
    const val ID_EVENT = "id_event"
    val routeWithArg = "$route/{$ID_EVENT}"
    override val titleRes = "Detail Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEventView (
    id_event: String,
    modifier: Modifier = Modifier,
    viewModel: DetailEventViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailEvent.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getDetailEvent()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_event) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon (
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Event"
                )
            }
        }
    ){  innerPadding -> val detailUiState by viewModel.detailUiState.collectAsState()
        BodyDetailEvent(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = {viewModel.getDetailEvent()}
        )}
}

@Composable
fun BodyDetailEvent (
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailUiState.Success->{
            Column (
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){  ItemDetailEvent (event = detailUiState.event) }
        }
        is DetailUiState.Error->{
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            Text("Unknown Error")
        }
    }
}

@Composable
fun ItemDetailEvent (event: Event)
{
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){  Column (modifier = Modifier.padding(16.dp)) {
        ComponentDetailEvent(judul = "ID Event", isinya = event.id_event)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailEvent(judul = "Nama Event", isinya = event.nama_event)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailEvent(judul = "Deskripsi Event", isinya = event.deskripsi_event)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailEvent(judul = "Tanggal Event", isinya = event.tanggal_event)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailEvent(judul = "Lokasi Event", isinya = event.lokasi_event)
    }
    }
}

@Composable
fun ComponentDetailEvent (
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul: ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}