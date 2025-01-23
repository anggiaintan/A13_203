package com.example.projectpam.repository

import com.example.projectpam.model.AllTransaksiResponse
import com.example.projectpam.model.Transaksi
import com.example.projectpam.service.TransaksiService
import java.io.IOException

interface TransaksiRepository {
    suspend fun getAllTransaksi(): AllTransaksiResponse
    suspend fun getTransaksiById(id: String): Transaksi
    suspend fun insertTransaksi(transaksi: Transaksi)
    suspend fun deleteTransaksi(id: String)
}

class NetworkTransaksiRepository (
    private val transaksiApiService: TransaksiService
) : TransaksiRepository {
    override suspend fun getAllTransaksi(): AllTransaksiResponse =
        transaksiApiService.getAllTransaksi()

    override suspend fun getTransaksiById(id: String): Transaksi {
        return transaksiApiService.getTransaksiById(id).data
    }

    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }

    override suspend fun deleteTransaksi(id: String) {
        try {
            val response = transaksiApiService.deleteTransaksi(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete transaksi. HTTP Status code: " +
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