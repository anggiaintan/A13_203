package com.example.projectpam.ui.viewmodel.viewmodeltiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectpam.model.Tiket
import com.example.projectpam.repository.TiketRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeTiketUiState {
    data class Success(val tiket: List<Tiket>) : HomeTiketUiState()
    object Error : HomeTiketUiState()
    object Loading : HomeTiketUiState()
}

class HomeTiketViewModel (private val tkt: TiketRepository) : ViewModel() {
    var tiketUiState: HomeTiketUiState by mutableStateOf(HomeTiketUiState.Loading)
        private set

    var daftarPeserta: List<String> by mutableStateOf(emptyList())
        private set

    var daftarEvent: List<String> by mutableStateOf(emptyList())
        private set

    init {
        getTiket()
        fetchDaftarPeserta()
        fetchDaftarEvent()
    }

    fun getTiket() {
        viewModelScope.launch {
            tiketUiState = HomeTiketUiState.Loading
            tiketUiState = try {
                HomeTiketUiState.Success(tkt.getAllTiket().data)
            } catch (e: IOException) {
                HomeTiketUiState.Error
            } catch (e: HttpException) {
                HomeTiketUiState.Error
            }
        }
    }

    fun addTiket(tiket: Tiket) {
        viewModelScope.launch {
            try {
                tkt.insertTiket(tiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = HomeTiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeTiketUiState.Error
            }
        }
    }

    fun editTiket(id_tiket: String, tiket: Tiket) {
        viewModelScope.launch {
            try {
                tkt.updateTiket(id_tiket, tiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = HomeTiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeTiketUiState.Error
            }
        }
    }

    fun deleteTiket(id_tiket: String) {
        viewModelScope.launch {
            try {
                tkt.deleteTiket(id_tiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = HomeTiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeTiketUiState.Error
            }
        }
    }

    fun getTiketById(id_tiket: String, onResult: (Tiket?) -> Unit) {
        viewModelScope.launch {
            val tiket = try {
                tkt.getTiketById(id_tiket)
            } catch (e: IOException) {
                null
            } catch (e: HttpException) {
                null
            }
            onResult(tiket)
        }
    }

    fun getJumlahTiketBerdasarkanKapasitas(kapasitas_tiket: Int): Int {
        val tiket = (tiketUiState as? HomeTiketUiState.Success)?.tiket ?: emptyList()
        return tiket.count { it.kapasitas_tiket == kapasitas_tiket }
    }

    fun getTotalHarga(jumlah: Int, hargaPerTiket: Int): Int {
        return jumlah * hargaPerTiket
    }

    private fun fetchDaftarPeserta() {
        viewModelScope.launch {
            daftarPeserta = try {
                tkt.getDaftarPeserta()
            } catch (e: IOException) {
                emptyList()
            } catch (e: HttpException) {
                emptyList()
            }
        }
    }

    private fun fetchDaftarEvent() {
        viewModelScope.launch {
            daftarEvent = try {
                tkt.getDaftarEvent()
            } catch (e: IOException) {
                emptyList()
            } catch (e: HttpException) {
                emptyList()
            }
        }
    }
}