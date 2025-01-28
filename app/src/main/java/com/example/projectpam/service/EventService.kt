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

    @GET("api/event")
    suspend fun getAllEvent(): AllEventResponse

    @GET("api/event/{id_event}")
    suspend fun getEventById(@Path("id_event") idEvent: String): EventDetailResponse

    @POST("api/event/storeevent")
    suspend fun insertEvent(@Body event: Event)

    @PUT("api/event/{id_event}")
    suspend fun updateEvent(@Path("id_event") idEvent: String, @Body event: Event)

    @DELETE("api/event/{id_event}")
    suspend fun deleteEvent(@Path("id_event") idEvent: String): retrofit2.Response<Void>

}