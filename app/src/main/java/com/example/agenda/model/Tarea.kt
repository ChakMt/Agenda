package com.example.agenda.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Tarea(
    @SerializedName("id") val id: Long?,
    @SerializedName ("FechaCreacion") val fechaCreacion: String,
    @SerializedName ("nombre") val nombre: String,
    @SerializedName ("descripcion") val descripcion: String,
    @SerializedName("prioridad") val prioridad: Int,
)