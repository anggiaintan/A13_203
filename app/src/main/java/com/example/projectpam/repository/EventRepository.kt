package com.example.projectpam.repository

import com.example.projectpam.model.AllEventResponse
import com.example.projectpam.model.Event
import com.example.projectpam.service.EventService
import java.io.IOException

interface EventRepository {
    suspend fun getAllEvent(): AllEventResponse
    suspend fun getEventById(idEvent: String): Event
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(idEvent: String, event: Event)
    suspend fun deleteEvent(idEvent: String)
}

class NetworkEventRepository (
    private val eventApiService: EventService
) : EventRepository {
    override suspend fun getAllEvent(): AllEventResponse =
        eventApiService.getAllEvent()

    override suspend fun getEventById(idEvent: String): Event {
        return eventApiService.getEventById(idEvent).data
    }

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(idEvent: String, event: Event) {
        eventApiService.updateEvent(idEvent, event)
    }

    override suspend fun deleteEvent(idEvent: String) {
        try {
            val response = eventApiService.deleteEvent(idEvent)
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