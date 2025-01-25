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
    var uiState by mutableStateOf(InsertUiState())
        private set
    fun updateInsertEventState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState (insertUiEvent = insertUiEvent)
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

data class InsertUiState (
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
)

data class InsertUiEvent (
    val id_event: String = "",
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = ""
)

fun InsertUiEvent.toEvent(): Event = Event (
    id_event = id_event,
    nama_event = namaEvent,
    deskripsi_event = deskripsiEvent,
    tanggal_event = tanggalEvent,
    lokasi_event = lokasiEvent
)

fun Event.toUiStateEvent(): InsertUiState = InsertUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Event.toInsertUiEvent(): InsertUiEvent = InsertUiEvent (
    id_event = id_event,
    namaEvent = nama_event,
    deskripsiEvent = deskripsi_event,
    tanggalEvent = tanggal_event,
    lokasiEvent = lokasi_event
)