package com.example.agenda.model

import com.google.gson.annotations.SerializedName

data class Evento(
    @SerializedName("id") val id: Long?,
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("descripcionEvento") val descripcionEvento: String?,
    @SerializedName("fechaIn") val fechaIn: String?,
    @SerializedName("fechaFin") val fechaFin: String?,
    @SerializedName("ubicacion") val ubicacion: String?,
    @SerializedName("participante") val participante: String?,
    @SerializedName("participantesAdicionales") val participantesAdicionales: String,
)