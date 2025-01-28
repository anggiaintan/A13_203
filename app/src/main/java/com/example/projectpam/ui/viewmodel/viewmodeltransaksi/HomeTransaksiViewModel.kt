package com.example.projectpam.ui.viewmodel.viewmodeltransaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectpam.model.Transaksi
import com.example.projectpam.repository.TransaksiRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeTransaksiUiState {
    data class Success(val transaksi: List<Transaksi>) : HomeTransaksiUiState()
    object Error : HomeTransaksiUiState()
    object Loading : HomeTransaksiUiState()
}

class HomeTransaksiViewModel (private val trans: TransaksiRepository) : ViewModel() {
    var transaksiUiState: HomeTransaksiUiState by mutableStateOf(HomeTransaksiUiState.Loading)
        private set

    init {
        getTransaksi()
    }

    fun getTransaksi() {
        viewModelScope.launch {
            transaksiUiState = HomeTransaksiUiState.Loading
            transaksiUiState = try {
                HomeTransaksiUiState.Success(trans.getAllTransaksi().data)
            } catch (e: IOException) {
                HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                HomeTransaksiUiState.Error
            }
        }
    }

    fun deleteTransaksi(idTransaksi: String) {
        viewModelScope.launch {
            try {
                trans.deleteTransaksi(idTransaksi)
                getTransaksi() // untuk refsresh data setelah dihapus
            } catch (e: IOException) {
                HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                HomeTransaksiUiState.Error
            }
        }
    }
}