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

    @GET ("api/tiket")
    suspend fun getAllTiket(): AllTiketResponse

    @GET ("api/tiket/{id_tiket}")
    suspend fun getTiketById(@Path("id_tiket") idTiket: String): TiketDetailResponse

    @POST("api/tiket/storetiket")
    suspend fun insertTiket(@Body tiket: Tiket)

    @PUT("api/tiket/{id_tiket}")
    suspend fun updateTiket(@Path("id_tiket") idTiket: String, @Body tiket: Tiket)

    @DELETE("api/tiket/{id_tiket}")
    suspend fun deleteTiket(@Path("id_tiket") idTiket: String): retrofit2.Response<Void>

    @GET("api/tiket/getpeserta")
    suspend fun getPesertaList(): List<String>

    @GET("api/tiket/getevent")
    suspend fun getEventList(): List<String>
}