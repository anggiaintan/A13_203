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

sealed class HomeUiState {
    data class Success(val tiket: List<Tiket>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeTiketViewModel (private val tkt: TiketRepository) : ViewModel() {
    var tiketUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
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
            tiketUiState = HomeUiState.Loading
            tiketUiState = try {
                HomeUiState.Success(tkt.getAllTiket().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun addTiket(tiket: Tiket) {
        viewModelScope.launch {
            try {
                tkt.insertTiket(tiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = HomeUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeUiState.Error
            }
        }
    }

    fun editTiket(idTiket: String, tiket: Tiket) {
        viewModelScope.launch {
            try {
                tkt.updateTiket(idTiket, tiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = HomeUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeUiState.Error
            }
        }
    }

    fun deleteTiket(idTiket: String) {
        viewModelScope.launch {
            try {
                tkt.deleteTiket(idTiket)
                getTiket()
            } catch (e: IOException) {
                tiketUiState = HomeUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeUiState.Error
            }
        }
    }

    fun getTiketById(idTiket: String, onResult: (Tiket?) -> Unit) {
        viewModelScope.launch {
            val tiket = try {
                tkt.getTiketById(idTiket)
            } catch (e: IOException) {
                null
            } catch (e: HttpException) {
                null
            }
            onResult(tiket)
        }
    }

    fun getJumlahTiketBerdasarkanKapasitas(kapasitas_tiket: Int): Int {
        val tiket = (tiketUiState as? HomeUiState.Success)?.tiket ?: emptyList()
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