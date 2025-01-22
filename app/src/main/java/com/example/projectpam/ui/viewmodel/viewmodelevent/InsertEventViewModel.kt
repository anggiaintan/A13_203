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
    val id_event: Int = 0,
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = ""
)

fun InsertUiEvent.toEvent(): Event = Event (
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)

fun Event.toUiStateEvent(): InsertUiState = InsertUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Event.toInsertUiEvent(): InsertUiEvent = InsertUiEvent (
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)