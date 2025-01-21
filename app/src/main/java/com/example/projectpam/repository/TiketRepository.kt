package com.example.projectpam.repository

import com.example.projectpam.model.AllTiketResponse
import com.example.projectpam.model.Tiket
import com.example.projectpam.service.TiketService
import okio.IOException

interface TiketRepository {
    suspend fun getAllTiket(): AllTiketResponse
    suspend fun getTiketById(id: Int): Tiket
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(id: Int, tiket: Tiket)
    suspend fun deleteTiket(id: Int)
}

class NetworkTiketRepository (
    private val tiketApiService: TiketService
) : TiketRepository {
    override suspend fun getAllTiket(): AllTiketResponse =
        tiketApiService.getAllTiket()

    override suspend fun getTiketById(id: Int): Tiket {
        return tiketApiService.getTiketById(id).data
    }

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(id: Int, tiket: Tiket) {
        tiketApiService.updateTiket(id, tiket)
    }

    override suspend fun deleteTiket(id: Int) {
        try {
            val response = tiketApiService.deleteTiket(id)
            if (!response.isSuccessful) {
                throw IOException (
                    "Failed to delete mahasiswa. HTTP Status code: " +
                    "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }
}