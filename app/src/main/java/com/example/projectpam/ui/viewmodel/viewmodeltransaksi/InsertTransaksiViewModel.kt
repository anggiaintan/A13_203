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
    var uiState by mutableStateOf(InsertUiState())
        private set
    fun updateInsertTransaksiState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState (insertUiEvent = insertUiEvent)
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

data class InsertUiState (
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
)

data class InsertUiEvent (
    val id_transaksi: String = "",
    val id_tiket: String = "",
    val jumlah_tiket: Int = 0,
    val jumlah_pembayaran: String = "",
    val tanggal_transaksi: String = ""
)

fun InsertUiEvent.toTransaksi(): Transaksi = Transaksi (
    id_transaksi = id_transaksi,
    id_tiket = id_tiket,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi
)

fun Transaksi.toUiStateTransaksi(): InsertUiState = InsertUiState (
    insertUiEvent = toInsertUiEvent()
)

fun Transaksi.toInsertUiEvent(): InsertUiEvent = InsertUiEvent (
    id_transaksi = id_transaksi,
    id_tiket = id_tiket,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi
)