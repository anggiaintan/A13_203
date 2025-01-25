package com.example.projectpam.ui.viewmodel.viewmodelevent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Event
import com.example.projectpam.repository.EventRepository
import kotlinx.coroutines.launch

class InsertEventViewModel (private val event: EventRepository): ViewModel() {
    var uiState by mutableStateOf(InsertEventUiState())
        private set
    fun updateInsertEventState(insertUiEvent: InsertEventUiEvent) {
        uiState = InsertEventUiState (insertUiEvent = insertUiEvent)
    }

    suspend fun insertEvent() {
        viewModelScope.launch {
            try {
                event.insertEvent(uiState.insertUiEvent.toEvent())
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
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = ""
)

fun InsertEventUiEvent.toEvent(): Event = Event (
    id_event = id_event,
    nama_event = namaEvent,
    deskripsi_event = deskripsiEvent,
    tanggal_event = tanggalEvent,
    lokasi_event = lokasiEvent
)

fun Event.toUiStateEvent(): InsertEventUiState = InsertEventUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Event.toInsertUiEvent(): InsertEventUiEvent = InsertEventUiEvent (
    id_event = id_event,
    namaEvent = nama_event,
    deskripsiEvent = deskripsi_event,
    tanggalEvent = tanggal_event,
    lokasiEvent = lokasi_event
)