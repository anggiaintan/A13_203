package com.example.projectpam.ui.view.viewtransaksi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectpam.R
import com.example.projectpam.model.Transaksi
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.HomeTransaksiViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.HomeUiState

object DestinasiHomeTransaksi : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTransaksiViewModel = viewModel( factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar (
                title = DestinasiHomeTransaksi.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTransaksi()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon (
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaksi")
            }
        },
    ) { innerPadding ->
        HomeStatus (
            homeUiState = viewModel.transaksiUiState,
            retryAction = {viewModel.getTransaksi()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteTransaksi(it.id_transaksi)
                viewModel.getTransaksi()
            }
        )
    }
}

@Composable
fun HomeStatus (
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.transaksi.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data transaksi")
                }
            } else {
                TransaksiLayout(
                    transaksi = homeUiState.transaksi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_transaksi)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )

            }
        is HomeUiState.Error -> OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading (modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction:() -> Unit, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text (text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TransaksiLayout (
    transaksi: List<Transaksi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Transaksi) -> Unit,
    onDeleteClick: (Transaksi) -> Unit = {}
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transaksi) { transaksi ->
            TransaksiCard(
                transaksi = transaksi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(transaksi) },
                onDeleteClick = {
                    onDeleteClick(it)
                }
            )
        }
    }
}

@Composable
fun TransaksiCard (
    transaksi: Transaksi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {}
) {
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text (
                    text = transaksi.id_transaksi,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(transaksi)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text (
                    text = transaksi.id_transaksi,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = transaksi.id_tiket,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = transaksi.jumlah_tiket.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = transaksi.jumlah_pembayaran.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = transaksi.tanggal_transaksi,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text (
                text = transaksi.id_transaksi,
                style = MaterialTheme.typography.titleMedium
            )
            Text (
                text = transaksi.id_tiket,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = transaksi.jumlah_tiket.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = transaksi.jumlah_pembayaran.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = transaksi.tanggal_transaksi,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        })
}