package com.example.projectpam.ui.view.viewpeserta

import androidx.compose.runtime.getValue
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectpam.model.Peserta
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.DetailPesertaViewModel
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.DetailUiState
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel

object DestinasiDetailPeserta : DestinasiNavigasi {
    override val route = "detail"
    const val ID_PESERTA = "id_peserta"
    val routeWithArg = "$route/{$ID_PESERTA}"
    override val titleRes = "Detail Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPesertaView (
    id_peserta: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPeserta.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getDetailPeserta()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_peserta) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon (
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Peserta"
                )
            }
        }
    ){  innerPadding -> val detailUiState by viewModel.detailUiState.collectAsState()
        BodyDetailPeserta(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = {viewModel.getDetailPeserta()}
        )}
}

@Composable
fun BodyDetailPeserta (
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
            ){  ItemDetailPeserta (peserta = detailUiState.peserta) }
        }
        is DetailUiState.Error->{
            OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }
        else -> {
            Text("Unknown Error")
        }
    }
}

@Composable
fun ItemDetailPeserta (peserta: Peserta)
{
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){  Column (modifier = Modifier.padding(16.dp)) {
        ComponentDetailPeserta(judul = "ID Peserta", isinya = peserta.id_peserta)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailPeserta(judul = "Nama", isinya = peserta.nama_peserta)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailPeserta(judul = "Email", isinya = peserta.email)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailPeserta(judul = "Nomor Telepon", isinya = peserta.nomor_telepon)
    }
    }
}

@Composable
fun ComponentDetailPeserta (
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