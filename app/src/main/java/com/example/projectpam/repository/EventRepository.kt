package com.example.projectpam.repository

import com.example.projectpam.model.AllEventResponse
import com.example.projectpam.model.Event
import com.example.projectpam.service.EventService
import java.io.IOException

interface EventRepository {
    suspend fun getAllEvent(): AllEventResponse
    suspend fun getEventById(id: String): Event
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(id: String, event: Event)
    suspend fun deleteEvent(id: String)
}

class NetworkEventRepository (
    private val eventApiService: EventService
) : EventRepository {
    override suspend fun getAllEvent(): AllEventResponse =
        eventApiService.getAllEvent()

    override suspend fun getEventById(id: String): Event {
        return eventApiService.getEventById(id).data
    }

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(id: String, event: Event) {
        eventApiService.updateEvent(id, event)
    }

    override suspend fun deleteEvent(id: String) {
        try {
            val response = eventApiService.deleteEvent(id)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete event. HTTP Status code: " +
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