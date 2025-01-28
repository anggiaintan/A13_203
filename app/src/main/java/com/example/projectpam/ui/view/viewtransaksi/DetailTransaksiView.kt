package com.example.projectpam.ui.view.viewtransaksi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.projectpam.model.Transaksi
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.DetailTransaksiViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.DetailTransaksiUiState


object DestinasiDetailTransaksi : DestinasiNavigasi {
    override val route = "detailTransaksi"
    const val ID_TRANSAKSI = "id_transaksi"
    val routeWithArg = "$route/{$ID_TRANSAKSI}"
    override val titleRes = "Detail Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaksiView (
    idTransaksi: String,
    modifier: Modifier = Modifier,
    viewModel: DetailTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTransaksi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getDetailTransaksi()}
            )
        }
    ){  innerPadding -> val detailUiState by viewModel.detailUiState.collectAsState()
        BodyDetailTransaksi(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = {viewModel.getDetailTransaksi()}
        )}
}

@Composable
fun BodyDetailTransaksi (
    modifier: Modifier = Modifier,
    detailUiState: DetailTransaksiUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailTransaksiUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailTransaksiUiState.Success->{
            Column (
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){  ItemDetailTransaksi (transaksi = detailUiState.transaksi) }
        }
        is DetailTransaksiUiState.Error->{
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
fun ItemDetailTransaksi (transaksi: Transaksi)
{
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){  Column (modifier = Modifier.padding(16.dp)) {
        ComponentDetailTransaksi(judul = "ID Transaksi", isinya = transaksi.id_transaksi)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTransaksi(judul = "ID Tiket", isinya = transaksi.id_tiket)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTransaksi(judul = "Jumlah Tiket", isinya = transaksi.jumlah_tiket.toString())
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTransaksi(judul = "Jumlah Pembayaran", isinya = transaksi.jumlah_pembayaran)
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentDetailTransaksi(judul = "Tanggal Transaksi", isinya = transaksi.tanggal_transaksi)
    }
    }
}

@Composable
fun ComponentDetailTransaksi (
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