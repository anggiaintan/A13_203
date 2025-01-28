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

    @GET("api/peserta")
    suspend fun getAllPeserta(): AllPesertaResponse

    @GET("api/peserta/{id_peserta}")
    suspend fun getPesertaById(@Path("id_peserta") idPeserta: String): PesertaDetailResponse

    @POST("api/peserta/storepeserta")
    suspend fun insertPeserta(@Body peserta: Peserta)

    @PUT("api/peserta/{id_peserta}")
    suspend fun updatePeserta(@Path("id_peserta") idPeserta: String, @Body peserta: Peserta)

    @DELETE("api/peserta/{id_peserta}")
    suspend fun deletePeserta(@Path("id_peserta") idPeserta: String): retrofit2.Response<Void>
}