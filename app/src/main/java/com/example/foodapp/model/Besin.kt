package com.example.foodapp.model

import java.io.Serializable


data class Besin(
    val besinAdi: String,
    val kalori: Long,
    val olcu: String,
    val kategoriID: Long)
    : Serializable
