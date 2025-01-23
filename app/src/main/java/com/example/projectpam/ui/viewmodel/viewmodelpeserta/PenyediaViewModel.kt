package com.example.projectpam.ui.viewmodel.viewmodelpeserta

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectpam.TiketkuApplication
import com.example.projectpam.ui.viewmodel.viewmodelevent.DetailEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodelevent.HomeEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodelevent.InsertEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodelevent.UpdateEventViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.DetailTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.HomeTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.InsertTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltiket.UpdateTiketViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.DetailTransaksiViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.HomeTransaksiViewModel
import com.example.projectpam.ui.viewmodel.viewmodeltransaksi.InsertTransaksiViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePesertaViewModel (aplikasiTiketku().container.pesertaRepository) }
        initializer { InsertPesertaViewModel (aplikasiTiketku().container.pesertaRepository) }
        initializer { DetailPesertaViewModel (createSavedStateHandle(), aplikasiTiketku().container.pesertaRepository) }
        initializer { UpdatePesertaViewModel (createSavedStateHandle(), aplikasiTiketku().container.pesertaRepository) }
        initializer { HomeEventViewModel (aplikasiTiketku().container.eventRepository) }
        initializer { InsertEventViewModel (aplikasiTiketku().container.eventRepository) }
        initializer { DetailEventViewModel (createSavedStateHandle(), aplikasiTiketku().container.eventRepository) }
        initializer { UpdateEventViewModel (createSavedStateHandle(), aplikasiTiketku().container.eventRepository) }
        initializer { HomeTiketViewModel (aplikasiTiketku().container.tiketRepository) }
        initializer { InsertTiketViewModel (aplikasiTiketku().container.tiketRepository) }
        initializer { DetailTiketViewModel (createSavedStateHandle(), aplikasiTiketku().container.tiketRepository) }
        initializer { UpdateTiketViewModel (createSavedStateHandle(), aplikasiTiketku().container.tiketRepository) }
        initializer { HomeTransaksiViewModel (aplikasiTiketku().container.transaksiRepository) }
        initializer { InsertTransaksiViewModel (aplikasiTiketku().container.transaksiRepository) }
        initializer { DetailTransaksiViewModel (createSavedStateHandle(), aplikasiTiketku().container.transaksiRepository) }
    }
}

fun CreationExtras.aplikasiTiketku(): TiketkuApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TiketkuApplication)