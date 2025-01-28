package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Peserta
import com.example.projectpam.repository.PesertaRepository
import com.example.projectpam.ui.view.viewpeserta.DestinasiUpdatePeserta
import kotlinx.coroutines.launch

class UpdatePesertaViewModel (
    savedStateHandle: SavedStateHandle,
    private val psrta: PesertaRepository
): ViewModel()
{
    val id_peserta: String = checkNotNull(savedStateHandle[DestinasiUpdatePeserta.ID_PESERTA])
    var uiState = mutableStateOf(InsertPesertaUiState())
    init {getPeserta()}
    private fun getPeserta(){
        viewModelScope.launch {
            try {
                val peserta = psrta.getPesertaById(id_peserta)
                peserta?.let {
                    uiState.value = it.toUiStatePeserta()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updatePeserta(id_peserta: String, peserta: Peserta) {
        viewModelScope.launch {
            try {
                psrta.updatePeserta(id_peserta, peserta)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePesertaState(insertUiEvent: InsertPesertaUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}
fun Peserta.toInsertUIEvent(): InsertPesertaUiState = InsertPesertaUiState (
    insertUiEvent = this.toDetailUiEvent()
)