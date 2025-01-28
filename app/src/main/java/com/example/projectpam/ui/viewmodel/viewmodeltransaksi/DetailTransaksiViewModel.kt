package com.example.projectpam.ui.viewmodel.viewmodeltransaksi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Transaksi
import com.example.projectpam.repository.TransaksiRepository
import com.example.projectpam.ui.view.viewtransaksi.DestinasiDetailTransaksi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailTransaksiUiState {
    data class Success (val transaksi: Transaksi) : DetailTransaksiUiState()
    object Error : DetailTransaksiUiState()
    object Loading : DetailTransaksiUiState()
}

class DetailTransaksiViewModel (
    savedStateHandle: SavedStateHandle,
    private val trans: TransaksiRepository
): ViewModel() {
    private val _idTransaksi: String = checkNotNull(savedStateHandle[DestinasiDetailTransaksi.ID_TRANSAKSI])
    private val _detailUiState = MutableStateFlow<DetailTransaksiUiState>(DetailTransaksiUiState.Loading)
    val detailUiState: StateFlow<DetailTransaksiUiState> = _detailUiState
    init { getDetailTransaksi() }
    fun getDetailTransaksi() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailTransaksiUiState.Loading
                val transaksi = trans.getTransaksiById(_idTransaksi)
                if (transaksi != null) {
                    _detailUiState.value = DetailTransaksiUiState.Success(transaksi)
                } else {
                    _detailUiState.value = DetailTransaksiUiState.Error }
            } catch (e: Exception) {
                _detailUiState.value = DetailTransaksiUiState.Error
            }
        }
    }
}

fun Transaksi.toDetailUiEvent(): InsertTransaksiUiEvent {
    return InsertTransaksiUiEvent (
        idTransaksi = id_transaksi,
        idTiket = id_tiket,
        jumlahTiket = jumlah_tiket,
        jumlahPembayaran = jumlah_pembayaran,
        tanggalTransaksi = tanggal_transaksi
    )
}