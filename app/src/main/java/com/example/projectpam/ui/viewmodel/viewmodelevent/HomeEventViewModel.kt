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

sealed class HomeUiState {
    data class Success(val event: List<Event>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeEventViewModel (private val evn: EventRepository) : ViewModel() {
    var eventUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getEvent()
    }

    fun getEvent() {
        viewModelScope.launch {
            eventUiState = HomeUiState.Loading
            eventUiState = try {
                HomeUiState.Success(evn.getAllEvent().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteEvent(id: Int) {
        viewModelScope.launch {
            try {
                evn.deleteEvent(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}