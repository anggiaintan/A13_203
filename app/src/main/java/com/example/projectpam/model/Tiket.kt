package com.example.projectpam.model

import kotlinx.serialization.Serializable

@Serializable
data class AllTiketResponse (
    val status: Boolean,
    val message: String,
    val data: List<Tiket>
)

@Serializable
data class TiketDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Tiket
)

@Serializable
data class Tiket (
    val id_tiket: String,
    val id_event: String,
    val id_pengguna: String,
    val kapasitas_tiket: Int,
    val harga_tiket: String
)