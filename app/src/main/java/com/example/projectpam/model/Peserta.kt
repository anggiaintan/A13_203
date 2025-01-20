package com.example.projectpam.model
import kotlinx.serialization.Serializable


@Serializable
data class PesertaDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Peserta
)

@Serializable
data class Peserta (
    val id_peserta: Int,
    val nama_peserta: String,
    val email: String,
    val nomor_telepon: String
)