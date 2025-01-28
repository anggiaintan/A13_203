package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectpam.model.Peserta
import com.example.projectpam.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Sealed class untuk merepresentasikan berbagai status UI
sealed class HomePesertaUiState {
    data class Success(val peserta: List<Peserta>) : HomePesertaUiState() // Status jika data berhasil didapatkan
    object Error : HomePesertaUiState() // Status jika terjadi error
    object Loading : HomePesertaUiState() // Status saat data sedang dimuat
}

// ViewModel untuk mengelola state dan logika bisnis data peserta
class HomePesertaViewModel (private val pesertaRepository: PesertaRepository) : ViewModel() {
    var pesertaUIState: HomePesertaUiState by mutableStateOf(HomePesertaUiState.Loading)
        private set

    init {
        getPeserta()
    }

    // Fungsi untuk mendapatkan daftar peserta dari repository
    fun getPeserta() {
        viewModelScope.launch {
            pesertaUIState = HomePesertaUiState.Loading
            pesertaUIState = try {
                HomePesertaUiState.Success(pesertaRepository.getAllPeserta().data)
            } catch (e: IOException) {
                HomePesertaUiState.Error
            } catch (e: HttpException) {
                HomePesertaUiState.Error
            }
        }
    }

    fun deletePeserta(id_peserta: String) {
        viewModelScope.launch {
            try {
                pesertaRepository.deletePeserta(id_peserta)
            } catch (e: IOException) {
                pesertaUIState = HomePesertaUiState.Error
            } catch (e: HttpException) {
                pesertaUIState = HomePesertaUiState.Error
            }
        }
    }
}