package com.example.agenda.model

import com.google.gson.annotations.SerializedName

data class Contacto(
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("fechaNacimiento") val fechaNacimiento: String,
    @SerializedName("correo") val correo: String
)
