package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Peserta
import com.example.projectpam.repository.PesertaRepository
import com.example.projectpam.ui.view.viewpeserta.DestinasiDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success (val peserta: Peserta) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailPesertaViewModel (
    savedStateHandle: SavedStateHandle,
    private val psrta: PesertaRepository
): ViewModel() {
    private val _id_Peserta: String = checkNotNull(savedStateHandle[DestinasiDetail.ID_PESERTA])
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState
    init { getDetailPeserta() }
    fun getDetailPeserta() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val peserta = psrta.getPesertaById(_id_Peserta)
                if (peserta != null) {
                    _detailUiState.value = DetailUiState.Success(peserta)
                } else {
                    _detailUiState.value = DetailUiState.Error }
                } catch (e: Exception) {
                    _detailUiState.value = DetailUiState.Error
            }
        }
    }
}

fun Peserta.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent (
        id_peserta = id_peserta,
        nama_peserta = nama_peserta,
        email = email,
        nomor_telepon = nomor_telepon
    )
}