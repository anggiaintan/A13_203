package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Peserta
import com.example.projectpam.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertPesertaViewModel (private val pesertaRepository: PesertaRepository): ViewModel() {
    var uiState by mutableStateOf(InsertPesertaUiState())
        private set
    fun updateInsertPesertaState(insertUiEvent: InsertPesertaUiEvent) {
        uiState = InsertPesertaUiState (insertUiEvent = insertUiEvent)
    }

    fun insertPeserta() {
        viewModelScope.launch {
            try {
                pesertaRepository.insertPeserta(uiState.insertUiEvent.toPeserta())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPesertaUiState (
    val insertUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent(),
)

data class InsertPesertaUiEvent (
    val id_peserta: String = "",
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = ""
)

fun InsertPesertaUiEvent.toPeserta(): Peserta = Peserta (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toUiStatePeserta(): InsertPesertaUiState = InsertPesertaUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Peserta.toInsertUiEvent(): InsertPesertaUiEvent = InsertPesertaUiEvent (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)