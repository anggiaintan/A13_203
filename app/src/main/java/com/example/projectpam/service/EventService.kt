package com.example.projectpam.service

import com.example.projectpam.model.AllEventResponse
import com.example.projectpam.model.Event
import com.example.projectpam.model.EventDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllEvent(): AllEventResponse

    @GET("{id}")
    suspend fun getEventById(@Path("id") id: Int): EventDetailResponse

    @POST("store")
    suspend fun insertEvent(@Body event: Event)

    @PUT("{id}")
    suspend fun updateEvent(@Path("id") id: Int, @Body event: Event)

    @DELETE("{id}")
    suspend fun deleteEvent(@Path("id") id: Int): retrofit2.Response<Void>

}