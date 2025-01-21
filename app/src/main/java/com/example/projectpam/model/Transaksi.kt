package com.example.projectpam.model

import kotlinx.serialization.Serializable

@Serializable
data class AllTransaksiResponse (
    val status: Boolean,
    val message: String,
    val data: List<Transaksi>
)

@Serializable
data class TransaksiDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Transaksi
)

@Serializable
data class Transaksi (
    val id_transaksi: Int,
    val id_tiket: Int,
    val jumlah_tiket: String,
    val jumlah_pembayaran: String,
    val tanggal_transaksi: String
)