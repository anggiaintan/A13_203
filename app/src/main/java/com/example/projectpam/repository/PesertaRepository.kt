package com.example.projectpam.repository

import com.example.projectpam.model.AllPesertaResponse
import com.example.projectpam.model.Peserta
import com.example.projectpam.service.PesertaService
import okio.IOException

interface PesertaRepository {
    suspend fun getAllPeserta(): AllPesertaResponse
    suspend fun getPesertaById(idPeserta: String): Peserta
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(idPeserta: String, peserta: Peserta)
    suspend fun deletePeserta(idPeserta: String)
}

class NetworkPesertaRepository (
private val pesertaApiService: PesertaService
) : PesertaRepository {
    override suspend fun getAllPeserta(): AllPesertaResponse =
        pesertaApiService.getAllPeserta()

    override suspend fun getPesertaById(idPeserta: String): Peserta {
        return pesertaApiService.getPesertaById(idPeserta).data
    }

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(idPeserta: String, peserta: Peserta) {
        pesertaApiService.updatePeserta(idPeserta, peserta)
    }

    override suspend fun deletePeserta(idPeserta: String) {
        try {
            val response = pesertaApiService.deletePeserta(idPeserta)
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