package com.example.projectpam.ui.viewmodel.viewmodeltransaksi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Transaksi
import com.example.projectpam.repository.TransaksiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success (val transaksi: Transaksi) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailTransaksiViewModel (
    savedStateHandle: SavedStateHandle,
    private val trans: TransaksiRepository
): ViewModel() {
    private val _id_Transaksi: String = checkNotNull(savedStateHandle[DestinasiDetail.ID_TRANSAKSI])
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState
    init { getDetailTransaksi() }
    fun getDetailTransaksi() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val transaksi = trans.getTransaksiById(_id_Transaksi)
                if (transaksi != null) {
                    _detailUiState.value = DetailUiState.Success(transaksi)
                } else {
                    _detailUiState.value = DetailUiState.Error }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}

fun Transaksi.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent (
        id_transaksi = id_transaksi,
        id_tiket = id_tiket,
        jumlah_tiket = jumlah_tiket,
        jumlah_pembayaran = jumlah_pembayaran,
        tanggal_transaksi = tanggal_transaksi
    )
}