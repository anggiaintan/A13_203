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

    @GET("/getevent")
    suspend fun getAllEvent(): AllEventResponse

    @GET("{id_event}")
    suspend fun getEventById(@Path("id") id: String): EventDetailResponse

    @POST("storeevent")
    suspend fun insertEvent(@Body event: Event)

    @PUT("{id_event}")
    suspend fun updateEvent(@Path("id") id: String, @Body event: Event)

    @DELETE("{id_event}")
    suspend fun deleteEvent(@Path("id") id: String): retrofit2.Response<Void>

}