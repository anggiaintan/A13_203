package com.example.projectpam.model
import kotlinx.serialization.Serializable

@Serializable
data class Peserta (
    val id_peserta: Int,
    val nama_peserta: String,
    val email: String,
    val nomor_telepon: String
)