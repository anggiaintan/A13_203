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


sealed class HomeUiState {
    data class Success(val peserta: List<Peserta>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomePesertaViewModel (private val psrta: PesertaRepository) : ViewModel() {
    var pesertaUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPeserta()
    }

    fun getPeserta() {
        viewModelScope.launch {
            pesertaUiState = HomeUiState.Loading
            pesertaUiState = try {
                HomeUiState.Success(psrta.getAllPeserta().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deletePeserta(id: Int) {
        viewModelScope.launch {
            try {
                psrta.deletePeserta(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}