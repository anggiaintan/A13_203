package com.example.projectpam.service

import com.example.projectpam.model.AllPesertaResponse
import com.example.projectpam.model.Peserta
import com.example.projectpam.model.PesertaDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PesertaService {
    @Headers (
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllPeserta(): AllPesertaResponse

    @GET("{id}")
    suspend fun getPesertaById(@Path("id") id: Int): PesertaDetailResponse

    @POST("store")
    suspend fun insertPeserta(@Body peserta: Peserta)

    @PUT("{id}")
    suspend fun updatePeserta(@Path("id") id: Int, @Body peserta: Peserta)

    @DELETE("{id}")
    suspend fun deletePeserta(@Path("id") id: Int): retrofit2.Response<Void>
}