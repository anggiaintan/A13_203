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
    private val baseUrl =  "https://3552-118-96-148-11.ngrok-free.app/"
        //"http://192.168.100.116:3000/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }

    private val eventService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }

    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }

    private val transaksiService: TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }

    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }

    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService)
    }
}