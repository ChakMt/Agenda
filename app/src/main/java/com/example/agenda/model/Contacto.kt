package com.example.agenda.model

import com.google.gson.annotations.SerializedName

data class Contacto(
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("apellidoMa") val apellidoMa: String?,
    @SerializedName("apellidoPa") val apellidoPa: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("direccion") val direccion: String?,
    @SerializedName("telefonoAdicional") val telefonoAdicional: String,
    @SerializedName("emailAdicional") val emailAdicional: String
)