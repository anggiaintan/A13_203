package com.example.projectpam.model

import kotlinx.serialization.Serializable

@Serializable
data class Tiket (
    val id_tiket: Int,
    val id_event: Int,
    val id_pengguna: Int,
    val kapasitas_tiket: String,
    val harga_tiket: String
)