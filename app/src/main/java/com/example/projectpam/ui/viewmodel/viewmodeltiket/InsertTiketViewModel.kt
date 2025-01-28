package com.example.projectpam.ui.viewmodel.viewmodeltiket

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Tiket
import com.example.projectpam.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(private val tiketRepository: TiketRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertTiketUiState())
        private set

    // Variabel untuk daftar event dan peserta
    var eventList by mutableStateOf<List<String>>(emptyList())
    var pesertaList by mutableStateOf<List<String>>(emptyList())

    init {
        getEventList() // ambil data event
        getPesertaList()
    }

    // Fungsi untuk update state saat memilih event dan peserta
    fun updateInsertTiketState(insertEventUiEvent: InsertTiketUiEvent) {
        uiState = InsertTiketUiState(insertUiEvent = insertEventUiEvent)
    }

    suspend fun insertTiket() {
        viewModelScope.launch {
            try {
                tiketRepository.insertTiket(uiState.insertUiEvent.toTiket())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getEventList() {
        viewModelScope.launch {
            eventList = tiketRepository.getDaftarEvent()
        }
    }

    private fun getPesertaList() {
        viewModelScope.launch {
            pesertaList = tiketRepository.getDaftarPeserta()
        }
    }
}

// UI State dan UI Event Data Classes
data class InsertTiketUiState(
    val insertUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
)

data class InsertTiketUiEvent(
    val id_tiket: String = "",
    val id_event: String = "",
    val id_peserta: String = "",
    val kapasitas_tiket: Int = 0,
    val harga_tiket: String = ""
)

fun InsertTiketUiEvent.toTiket(): Tiket = Tiket(
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Tiket.toInsertUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent(
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)
