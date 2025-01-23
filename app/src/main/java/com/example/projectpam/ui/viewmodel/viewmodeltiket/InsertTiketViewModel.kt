package com.example.projectpam.ui.viewmodel.viewmodeltiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Tiket
import com.example.projectpam.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel (private val tiket: TiketRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set
    fun updateInsertTiketState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState (insertUiEvent = insertUiEvent)
    }

    suspend fun insertTiket() {
        viewModelScope.launch {
            try {
                tiket.insertTiket(uiState.insertUiEvent.toTiket())
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
    val id_tiket: Int = 0,
    val id_event: Int = 0,
    val id_pengguna: Int = 0,
    val kapasitas_tiket: String = "",
    val harga_tiket: String = ""
)

fun InsertUiEvent.toTiket(): Tiket = Tiket (
    id_tiket = id_tiket,
    id_event = id_event,
    id_pengguna = id_pengguna,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)

fun Tiket.toUiStateTiket(): InsertUiState = InsertUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Tiket.toInsertUiEvent(): InsertUiEvent = InsertUiEvent (
    id_tiket = id_tiket,
    id_event = id_event,
    id_pengguna = id_pengguna,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)