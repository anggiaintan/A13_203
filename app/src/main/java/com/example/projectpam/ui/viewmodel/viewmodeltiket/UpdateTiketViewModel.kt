package com.example.projectpam.ui.viewmodel.viewmodeltiket

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Tiket
import com.example.projectpam.repository.TiketRepository
import com.example.projectpam.ui.view.viewtiket.DestinasiUpdateTiket
import kotlinx.coroutines.launch

class UpdateTiketViewModel (
    savedStateHandle: SavedStateHandle,
    private val tkt: TiketRepository
): ViewModel()
{
    val id_tiket: String = checkNotNull(savedStateHandle[DestinasiUpdateTiket.ID_TIKET])
    var uiState = mutableStateOf(InsertTiketUiState())
    init {getTiket()}
    private fun getTiket(){
        viewModelScope.launch {
            try {
                val tiket = tkt.getTiketById(id_tiket)
               tiket?.let {
                    uiState.value = it.toInsertUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateTiket(id_tiket: String, tiket: Tiket) {
        viewModelScope.launch {
            try {
                tkt.updateTiket(id_tiket, tiket)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTiketState(insertUiEvent: InsertTiketUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}
fun Tiket.toInsertUIEvent(): InsertTiketUiState = InsertTiketUiState (
    insertUiEvent = this.toDetailUiEvent()
)