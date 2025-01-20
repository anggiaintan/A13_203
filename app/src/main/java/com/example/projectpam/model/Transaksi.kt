package com.example.projectpam.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaksi (
    val id_transaksi: Int,
    val id_tiket: Int,
    val jumlah_tiket: String,
    val jumlah_pembayaran: String,
    val tanggal_transaksi: String
)