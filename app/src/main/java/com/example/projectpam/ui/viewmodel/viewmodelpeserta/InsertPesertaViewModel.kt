package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Peserta
import com.example.projectpam.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertPesertaViewModel (private val peserta: PesertaRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set
    fun updateInsertPesertaState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState (insertUiEvent = insertUiEvent)
    }

    suspend fun insertPeserta() {
        viewModelScope.launch {
            try {
                peserta.insertPeserta(uiState.insertUiEvent.toPeserta())
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
    val id_peserta: String = "",
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = ""
)

fun InsertUiEvent.toPeserta(): Peserta = Peserta (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toUiStatePeserta(): InsertUiState = InsertUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Peserta.toInsertUiEvent(): InsertUiEvent = InsertUiEvent (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)