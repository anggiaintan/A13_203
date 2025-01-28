package com.example.projectpam.ui.viewmodel.viewmodelevent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectpam.model.Event
import com.example.projectpam.repository.EventRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeEventUiState {
    data class Success(val event: List<Event>) : HomeEventUiState()
    object Error : HomeEventUiState()
    object Loading : HomeEventUiState()
}

class HomeEventViewModel (private val eventRepository: EventRepository) : ViewModel() {
    var eventUIState: HomeEventUiState by mutableStateOf(HomeEventUiState.Loading)
        private set

    init {
        getEvent()
    }

    fun getEvent() {
        viewModelScope.launch {
            eventUIState = HomeEventUiState.Loading
            eventUIState = try {
                HomeEventUiState.Success(eventRepository.getAllEvent().data)
            } catch (e: IOException) {
                HomeEventUiState.Error
            } catch (e: HttpException) {
                HomeEventUiState.Error
            }
        }
    }

    fun deleteEvent(id_event: String) {
        viewModelScope.launch {
            try {
                eventRepository.deleteEvent(id_event)
            } catch (e: IOException) {
                eventUIState = HomeEventUiState.Error
            } catch (e: HttpException) {
                eventUIState = HomeEventUiState.Error
            }
        }
    }
}