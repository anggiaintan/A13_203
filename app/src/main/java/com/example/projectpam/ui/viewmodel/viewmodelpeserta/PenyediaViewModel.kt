package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectpam.TiketkuApplication

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePesertaViewModel (aplikasiTiketku().container.pesertaRepository) }
        initializer { InsertPesertaViewModel (aplikasiTiketku().container.pesertaRepository) }
        initializer { DetailPesertaViewModel (createSavedStateHandle(), aplikasiTiketku().container.pesertaRepository) }
        initializer { UpdatePesertaViewModel (createSavedStateHandle(), aplikasiTiketku().container.pesertaRepository) }
    }
}

fun CreationExtras.aplikasiTiketku(): TiketkuApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TiketkuApplication)