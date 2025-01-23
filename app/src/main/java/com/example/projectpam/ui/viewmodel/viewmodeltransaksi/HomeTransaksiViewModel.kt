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


sealed class HomeUiState {
    data class Success(val transaksi: List<Transaksi>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeTransaksiViewModel (private val trans: TransaksiRepository) : ViewModel() {
    var transaksiUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getTransaksi()
    }

    fun getTransaksi() {
        viewModelScope.launch {
            transaksiUiState = HomeUiState.Loading
            transaksiUiState = try {
                HomeUiState.Success(trans.getAllTransaksi().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteTransaksi(id: String) {
        viewModelScope.launch {
            try {
                trans.deleteTransaksi(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}