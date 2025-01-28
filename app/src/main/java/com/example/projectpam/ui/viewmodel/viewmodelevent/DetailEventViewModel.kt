package com.example.projectpam.ui.viewmodel.viewmodelevent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Event
import com.example.projectpam.repository.EventRepository
import com.example.projectpam.ui.view.viewevent.DestinasiDetailEvent
import com.example.projectpam.ui.viewmodel.viewmodelpeserta.InsertEventUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailEventUiState {
    data class Success (val event: Event) : DetailEventUiState()
    object Error : DetailEventUiState()
    object Loading : DetailEventUiState()
}

class DetailEventViewModel (
    savedStateHandle: SavedStateHandle,
    private val evn: EventRepository
): ViewModel() {
    private val _idEvent: String = checkNotNull(savedStateHandle[DestinasiDetailEvent.ID_EVENT])
    private val _detailUiState = MutableStateFlow<DetailEventUiState>(DetailEventUiState.Loading)
    val detailUiState: StateFlow<DetailEventUiState> = _detailUiState
    init { getDetailEvent() }
    fun getDetailEvent() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailEventUiState.Loading
                val event = evn.getEventById(_idEvent)
                if (event != null) {
                    _detailUiState.value = DetailEventUiState.Success(event)
                } else {
                    _detailUiState.value = DetailEventUiState.Error }
            } catch (e: Exception) {
                _detailUiState.value = DetailEventUiState.Error
            }
        }
    }
}

fun Event.toDetailUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent (
        id_event = id_event,
        nama_event = nama_event,
        deskripsi_event = deskripsi_event,
        tanggal_event = tanggal_event,
        lokasi_event = lokasi_event
    )
}