package com.example.projectpam.dependeciesinjection

import com.example.projectpam.repository.EventRepository
import com.example.projectpam.repository.NetworkEventRepository
import com.example.projectpam.repository.NetworkPesertaRepository
import com.example.projectpam.repository.NetworkTiketRepository
import com.example.projectpam.repository.NetworkTransaksiRepository
import com.example.projectpam.repository.PesertaRepository
import com.example.projectpam.repository.TiketRepository
import com.example.projectpam.repository.TransaksiRepository
import com.example.projectpam.service.EventService
import com.example.projectpam.service.PesertaService
import com.example.projectpam.service.TiketService
import com.example.projectpam.service.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pesertaRepository: PesertaRepository
    val eventRepository: EventRepository
    val tiketRepository: TiketRepository
    val transaksiRepository: TransaksiRepository
}

class ContainerApp: AppContainer {
    private val pesertabaseUrl = "http://192.168.0.106:3000/api/peserta"
    private val eventbaseUrl = "http://192.168.0.106:3000/api/event"
    private val tiketbaseUrl = "http://192.168.0.106:3000/api/tiket"
    private val transaksibaseUrl = "http://192.168.0.106:3000/api/transaksi"

    private val json = Json { ignoreUnknownKeys = true }

    // peserta
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(pesertabaseUrl)
        .build()

    // event
    private val retrofitEvent: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(eventbaseUrl)
        .build()

    // tiket
    private val retrofitTiket: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(tiketbaseUrl)
        .build()

    // transaksi
    private val retrofitTransaksi: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(transaksibaseUrl)
        .build()

    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }
    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    private val eventService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }
    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }
    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }

    private val transaksiService: TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }
    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService)
    }
}