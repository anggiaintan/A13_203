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

    @GET("/gettransaksi")
    suspend fun getAllTransaksi(): AllTransaksiResponse

    @GET("{id_transaksi}")
    suspend fun getTransaksiById(@Path("id_transaksi") idTransaksi: String): TransaksiDetailResponse

    @POST("storetransaksi")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @DELETE("{id_transaksi}")
    suspend fun deleteTransaksi(@Path("id_transaksi") idTransaksi: String): retrofit2.Response<Void>
}