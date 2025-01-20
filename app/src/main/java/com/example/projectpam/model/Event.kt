package com.example.projectpam.model

import kotlinx.serialization.Serializable

@Serializable
data class EventDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Event
)

@Serializable
data class Event (
    val id_event: Int,
    val nama_event: String,
    val deskripsi_event: String,
    val tanggal_event: String,
    val lokasi_event: String
)