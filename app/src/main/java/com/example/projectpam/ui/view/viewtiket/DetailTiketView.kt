package com.example.projectpam.ui.view.viewtiket

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
import com.example.projectpam.model.Tiket
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.DetailTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.DetailTiketUiState

object DestinasiDetailTiket : DestinasiNavigasi {
    override val route = "detailTiket"
    const val ID_TIKET = "id_tiket"
    val routeWithArg = "$route/{$ID_TIKET}"
    override val titleRes = "Detail TIKET"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTiketView (
    idTiket: String,
    modifier: Modifier = Modifier,
    viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTiket.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getDetailTiket()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idTiket.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon (
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tiket"
                )
            }
        }
    ){  innerPadding -> val detailUiState by viewModel.detailUiState.collectAsState()
        BodyDetailTiket(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = {viewModel.getDetailTiket()}
        )}
}

@Composable
fun BodyDetailTiket (
    modifier: Modifier = Modifier,
    detailUiState: DetailTiketUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailTiketUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailTiketUiState.Success->{
            Column (
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){  ItemDetailTiket (tiket = detailUiState.tiket) }
        }
        is DetailTiketUiState.Error->{
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
fun ItemDetailTiket (tiket: Tiket)
{
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){  Column (modifier = Modifier.padding(16.dp)) {
        ComponentDetailTiket(judul = "ID Tiket", isinya = tiket.id_tiket)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTiket(judul = "ID Event", isinya = tiket.id_event)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTiket(judul = "ID Peserta", isinya = tiket.id_peserta)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTiket(judul = "Kapasitas Tiket", isinya = tiket.kapasitas_tiket.toString())
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTiket(judul = "Harga Tiket", isinya = tiket.harga_tiket)
    }
    }
}

@Composable
fun ComponentDetailTiket (
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