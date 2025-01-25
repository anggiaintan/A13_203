package com.example.projectpam.ui.viewmodel.viewmodeltiket

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Tiket
import com.example.projectpam.repository.TiketRepository
import com.example.projectpam.ui.view.viewtiket.DestinasiDetailTiket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success (val tiket: Tiket) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailTiketViewModel (
    savedStateHandle: SavedStateHandle,
    private val tkt: TiketRepository
): ViewModel() {
    private val _idTiket: String = checkNotNull(savedStateHandle[DestinasiDetailTiket.ID_TIKET])
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState
    init { getDetailTiket() }
    fun getDetailTiket() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val tiket = tkt.getTiketById(_idTiket)
                if (tiket != null) {
                    _detailUiState.value = DetailUiState.Success(tiket)
                } else {
                    _detailUiState.value = DetailUiState.Error }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}

fun Tiket.toDetailUiEvent(): InsertTiketUiEvent {
    return InsertTiketUiEvent (
        id_tiket = id_tiket,
        id_event = id_event,
        id_pengguna = id_pengguna,
        kapasitas_tiket = kapasitas_tiket,
        harga_tiket = harga_tiket
    )
}