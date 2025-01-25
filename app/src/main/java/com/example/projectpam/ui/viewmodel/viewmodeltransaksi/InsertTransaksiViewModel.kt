package com.example.projectpam.ui.viewmodel.viewmodeltransaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectpam.model.Transaksi
import com.example.projectpam.repository.TransaksiRepository
import kotlinx.coroutines.launch


class InsertTransaksiViewModel (private val transaksi: TransaksiRepository): ViewModel() {
    var uiState by mutableStateOf(InsertTransaksiUiState())
        private set
    fun updateInsertTransaksiState(insertUiEvent: InsertTransaksiUiEvent) {
        uiState = InsertTransaksiUiState (insertUiEvent = insertUiEvent)
    }

    suspend fun insertTransaksi() {
        viewModelScope.launch {
            try {
                transaksi.insertTransaksi(uiState.insertUiEvent.toTransaksi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTransaksiUiState (
    val insertUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent(),
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

fun Transaksi.toUiStateTransaksi(): InsertTransaksiUiState = InsertTransaksiUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Transaksi.toInsertUiEvent(): InsertTransaksiUiEvent = InsertTransaksiUiEvent (
    idTransaksi = id_transaksi,
    idTiket = id_tiket,
    jumlahTiket = jumlah_tiket,
    jumlahPembayaran = jumlah_pembayaran,
    tanggalTransaksi = tanggal_transaksi
)