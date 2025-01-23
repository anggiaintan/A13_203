package com.example.projectpam.ui.viewmodel.viewmodelevent

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Event
import com.example.projectpam.repository.EventRepository
import kotlinx.coroutines.launch

class UpdateEventViewModel (
    savedStateHandle: SavedStateHandle,
    private val evn: EventRepository
): ViewModel()
{
    val id_event: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID_EVENT])
    var uiState = mutableStateOf(InsertUiState())
    init {getEvent()}
    private fun getEvent(){
        viewModelScope.launch {
            try {
                val event = evn.getEventById(id_event)
                event?.let {
                    uiState.value = it.toInsertUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateEvent(id_event: String, event: Event) {
        viewModelScope.launch {
            try {
                evn.updateEvent(id_event, event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateEventState(insertUiEvent: InsertUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}
fun Event.toInsertUIEvent(): InsertUiState = InsertUiState (
    insertUiEvent = this.toDetailUiEvent()
)