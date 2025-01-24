package com.example.projectpam.repository

import com.example.projectpam.model.AllTiketResponse
import com.example.projectpam.model.Tiket
import com.example.projectpam.service.TiketService
import okio.IOException

interface TiketRepository {
    suspend fun getAllTiket(): AllTiketResponse
    suspend fun getTiketById(idTiket: String): Tiket
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(idTiket: String, tiket: Tiket)
    suspend fun deleteTiket(idTiket: String)

    // Fungsi untuk mendapatkan data peserta dan event
    suspend fun getDaftarPeserta(): List<String>
    suspend fun getDaftarEvent(): List<String>
}

class NetworkTiketRepository (
    private val tiketApiService: TiketService
) : TiketRepository {
    override suspend fun getAllTiket(): AllTiketResponse =
        tiketApiService.getAllTiket()

    override suspend fun getTiketById(idTiket: String): Tiket {
        return tiketApiService.getTiketById(idTiket).data
    }

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(idTiket: String, tiket: Tiket) {
        tiketApiService.updateTiket(idTiket, tiket)
    }

    override suspend fun deleteTiket(idTiket: String) {
        try {
            val response = tiketApiService.deleteTiket(idTiket)
            if (!response.isSuccessful) {
                throw IOException (
                    "Failed to delete tiket. HTTP Status code: " +
                    "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    // Implementasi fungsi tambahan
    override suspend fun getDaftarPeserta(): List<String> {
        return try {
            tiketApiService.getPesertaList() // Asumsikan ada endpoint ini
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getDaftarEvent(): List<String> {
        return try {
            tiketApiService.getEventList() // Asumsikan ada endpoint ini
        } catch (e: Exception) {
            emptyList()
        }
    }
}