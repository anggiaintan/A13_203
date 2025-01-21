package com.example.projectpam.service

import com.example.projectpam.model.AllTransaksiResponse
import com.example.projectpam.model.Transaksi
import com.example.projectpam.model.TransaksiDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransaksiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllTransaksi(): AllTransaksiResponse

    @GET("{id}")
    suspend fun getTransaksiById(@Path("id") id: Int): TransaksiDetailResponse

    @POST("store")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @DELETE("{id}")
    suspend fun deleteTransaksi(@Path("id") id: Int): retrofit2.Response<Void>
}