package com.example.projectpam.dependeciesinjection

import com.example.projectpam.repository.EventRepository
import com.example.projectpam.repository.PesertaRepository
import com.example.projectpam.repository.TiketRepository
import com.example.projectpam.repository.TransaksiRepository

interface InterfaceContainerApp {
    val pesertaRepository: PesertaRepository
    val eventRepository: EventRepository
    val tiketRepository: TiketRepository
    val transaksiRepository: TransaksiRepository
}