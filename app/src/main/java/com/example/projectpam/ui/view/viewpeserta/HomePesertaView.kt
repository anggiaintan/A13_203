package com.example.projectpam.ui.view.viewpeserta

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
import androidx.navigation.NavController
import com.example.projectpam.R
import com.example.projectpam.model.Peserta
import com.example.projectpam.navigation.DestinasiNavigasi
import com.example.projectpam.ui.customwidget.CostumeTopAppBar
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.HomePesertaViewModel
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.HomePesertaUiState
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.PenyediaViewModel

object DestinasiHomePeserta : DestinasiNavigasi {
    override val route = "homePeserta"
    override val titleRes = "Home Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesertaHomeScreen (
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
    viewModel: HomePesertaViewModel = viewModel( factory = PenyediaViewModel.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar (
                title = DestinasiHomePeserta.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPeserta()
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
                    contentDescription = "Add Peserta")
            }
        },
    ) { innerPadding ->
        HomeStatus (
            homeUiState = viewModel.pesertaUIState,
            retryAction = {viewModel.getPeserta()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deletePeserta(it.id_peserta)
                viewModel.getPeserta()
            }
        )
    }
}

@Composable
fun HomeStatus (
    homeUiState: HomePesertaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
) {
    when (homeUiState) {
        is HomePesertaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePesertaUiState.Success ->
            if (homeUiState.peserta.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data peserta")
                }
            } else {
                PesertaLayout(
                    peserta = homeUiState.peserta,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_peserta)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )

            }
        is HomePesertaUiState.Error -> OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
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
fun PesertaLayout (
    peserta: List<Peserta>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peserta) -> Unit,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peserta) { peserta ->
            PesertaCard(
                peserta = peserta,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(peserta) },
                onDeleteClick = {
                    onDeleteClick(peserta)
                }
            )
        }
    }
}

@Composable
fun PesertaCard (
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {}
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
                    text = peserta.id_peserta,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(peserta)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text (
                    text = peserta.nama_peserta,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text (
                text = peserta.email,
                style = MaterialTheme.typography.titleMedium
            )
            Text (
                text = peserta.nomor_telepon,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}