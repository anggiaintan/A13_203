package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Peserta
import com.example.projectpam.repository.PesertaRepository
import com.example.projectpam.ui.view.viewpeserta.DestinasiDetailPeserta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailPesertaUiState {
    data class Success (val peserta: Peserta) : DetailPesertaUiState()
    object Error : DetailPesertaUiState()
    object Loading : DetailPesertaUiState()
}

class DetailPesertaViewModel (
    savedStateHandle: SavedStateHandle,
    private val psrta: PesertaRepository
): ViewModel() {
    private val _idPeserta: String = checkNotNull(savedStateHandle[DestinasiDetailPeserta.ID_PESERTA])
    private val _detailUiState = MutableStateFlow<DetailPesertaUiState>(DetailPesertaUiState.Loading)
    val detailUiState: StateFlow<DetailPesertaUiState> = _detailUiState
    init { getDetailPeserta() }
    fun getDetailPeserta() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailPesertaUiState.Loading
                val peserta = psrta.getPesertaById(_idPeserta)
                if (peserta != null) {
                    _detailUiState.value = DetailPesertaUiState.Success(peserta)
                } else {
                    _detailUiState.value = DetailPesertaUiState.Error }
                } catch (e: Exception) {
                    _detailUiState.value = DetailPesertaUiState.Error
            }
        }
    }
}

fun Peserta.toDetailUiEvent(): InsertPesertaUiEvent {
    return InsertPesertaUiEvent (
        id_peserta = id_peserta,
        nama_peserta = nama_peserta,
        email = email,
        nomor_telepon = nomor_telepon
    )
}