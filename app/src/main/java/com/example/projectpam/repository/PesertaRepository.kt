package com.example.projectpam.repository

import com.example.projectpam.model.AllPesertaResponse
import com.example.projectpam.model.Peserta
import com.example.projectpam.service.PesertaService
import okio.IOException

interface PesertaRepository {
    suspend fun getAllPeserta(): AllPesertaResponse
    suspend fun getPesertaById(id: String): Peserta
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(id: String, peserta: Peserta)
    suspend fun deletePeserta(id: String)
}

class NetworkPesertaRepository (
private val pesertaApiService: PesertaService
) : PesertaRepository {
    override suspend fun getAllPeserta(): AllPesertaResponse =
        pesertaApiService.getAllPeserta()

    override suspend fun getPesertaById(id: String): Peserta {
        return pesertaApiService.getPesertaById(id).data
    }

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(id: String, peserta: Peserta) {
        pesertaApiService.updatePeserta(id, peserta)
    }

    override suspend fun deletePeserta(id: String) {
        try {
            val response = pesertaApiService.deletePeserta(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete peserta. HTTP Status code: " +
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