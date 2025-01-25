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
    var uiState by mutableStateOf(InsertTiketUiState())
        private set
    fun updateInsertTiketState(insertUiEvent: InsertTiketUiEvent) {
        uiState = InsertTiketUiState (insertUiEvent = insertUiEvent)
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

data class InsertTiketUiState (
    val insertUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
)

data class InsertTiketUiEvent (
    val id_tiket: String = "",
    val id_event: String = "",
    val id_pengguna: String = "",
    val kapasitas_tiket: Int = 0,
    val harga_tiket: String = ""
)

fun InsertTiketUiEvent.toTiket(): Tiket = Tiket (
    id_tiket = id_tiket,
    id_event = id_event,
    id_pengguna = id_pengguna,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Tiket.toInsertUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent (
    id_tiket = id_tiket,
    id_event = id_event,
    id_pengguna = id_pengguna,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)