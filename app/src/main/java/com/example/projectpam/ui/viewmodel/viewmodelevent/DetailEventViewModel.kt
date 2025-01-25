package com.example.projectpam.ui.viewmodel.viewmodelevent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Event
import com.example.projectpam.repository.EventRepository
import com.example.projectpam.ui.view.viewevent.DestinasiDetailEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success (val event: Event) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailEventViewModel (
    savedStateHandle: SavedStateHandle,
    private val evn: EventRepository
): ViewModel() {
    private val _idEvent: String = checkNotNull(savedStateHandle[DestinasiDetailEvent.ID_EVENT])
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState
    init { getDetailEvent() }
    fun getDetailEvent() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailUiState.Loading
                val event = evn.getEventById(_idEvent)
                if (event != null) {
                    _detailUiState.value = DetailUiState.Success(event)
                } else {
                    _detailUiState.value = DetailUiState.Error }
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}

fun Event.toDetailUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent (
        id_event = id_event,
        namaEvent = nama_event,
        deskripsiEvent = deskripsi_event,
        tanggalEvent = tanggal_event,
        lokasiEvent = lokasi_event
    )
}