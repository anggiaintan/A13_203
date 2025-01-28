package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Event
import com.example.projectpam.repository.EventRepository
import kotlinx.coroutines.launch

class InsertEventViewModel (private val eventRepository: EventRepository): ViewModel() {
    var uiState by mutableStateOf(InsertEventUiState())
        private set
    fun updateInsertEvnState(insertUiEvent: InsertEventUiEvent) {
        uiState = InsertEventUiState (insertUiEvent = insertUiEvent)
    }

    suspend fun insertEvn() {
        viewModelScope.launch {
            try {
                eventRepository.insertEvent(uiState.insertUiEvent.toEvn())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertEventUiState (
    val insertUiEvent: InsertEventUiEvent = InsertEventUiEvent(),
)

data class InsertEventUiEvent (
    val id_event: String = "",
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = ""
)

fun InsertEventUiEvent.toEvn(): Event = Event (
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)

fun Event.toUiStateEvent(): InsertEventUiState = InsertEventUiState (
    insertUiEvent = toInsertEventUiEvent()
)

fun Event.toInsertEventUiEvent(): InsertEventUiEvent = InsertEventUiEvent (
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)