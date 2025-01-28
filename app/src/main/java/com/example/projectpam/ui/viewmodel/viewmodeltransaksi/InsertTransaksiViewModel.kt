package com.example.projectpam.ui.viewmodel.viewmodeltransaksi

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Tiket
import com.example.projectpam.model.Transaksi
import com.example.projectpam.repository.TiketRepository
import com.example.projectpam.repository.TransaksiRepository
import kotlinx.coroutines.launch


class InsertTransaksiViewModel (
    private val transaksiRepository: TransaksiRepository,
    private val tiketRepository: TiketRepository

): ViewModel() {
    var uiState by mutableStateOf(InsertTransaksiUiState())
        private set

    var listTiket by mutableStateOf<List<Tiket>>(listOf())
        private set

    init {
        loadExistData()
    }

    private fun loadExistData() {
        viewModelScope.launch {
            try {
                val tiketResponse = tiketRepository.getAllTiket()
                listTiket = tiketResponse.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertTransaksiState(insertUiEvent: InsertTransaksiUiEvent) {
        uiState = uiState.copy (insertUiEvent = insertUiEvent)
    }

    fun insertTransaksi() {
        viewModelScope.launch {
            try {
                transaksiRepository.insertTransaksi(uiState.insertUiEvent.toTransaksi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTransaksiUiState (
    val insertUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent(),
    val listTiket: List<Tiket> = emptyList()
)

data class InsertTransaksiUiEvent (
    val idTransaksi: String = "",
    val idTiket: String = "",
    val jumlahTiket: Int = 0,
    val jumlahPembayaran: String = "",
    val tanggalTransaksi: String = ""
)

fun InsertTransaksiUiEvent.toTransaksi(): Transaksi = Transaksi (
    id_transaksi = idTransaksi,
    id_tiket = idTiket,
    jumlah_tiket = jumlahTiket,
    jumlah_pembayaran = jumlahPembayaran,
    tanggal_transaksi = tanggalTransaksi
)

fun validateFields(insertUiEvent: InsertTransaksiUiEvent): Boolean {
    return insertUiEvent.idTiket.isNotEmpty() &&
            insertUiEvent.tanggalTransaksi.isNotEmpty()
}