package com.example.projectpam.service

import com.example.projectpam.model.AllTiketResponse
import com.example.projectpam.model.Tiket
import com.example.projectpam.model.TiketDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketService {
    @Headers (
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET (".")
    suspend fun getAllTiket(): AllTiketResponse

    @GET ("{id}")
    suspend fun getTiketById(@Path("id") id: String): TiketDetailResponse

    @POST("store")
    suspend fun insertTiket(@Body tiket: Tiket)

    @PUT("{id}")
    suspend fun updateTiket(@Path("id") id: String, @Body tiket: Tiket)

    @DELETE("{id}")
    suspend fun deleteTiket(@Path("id") id: String): retrofit2.Response<Void>

    @GET("peserta")
    suspend fun getPesertaList(): List<String>

    @GET("event")
    suspend fun getEventList(): List<String>
}