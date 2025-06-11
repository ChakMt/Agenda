package com.example.agenda.service

import com.example.agenda.model.Tarea
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.ArrayList

internal interface Tareaservice {
    @POST("tarea/")
    fun getAlL(): Call<java.util.List<Tarea>>

    @POST("tarea/add")
    fun add(@Body tarea: Tarea): Call<Tarea>

    @POST("tarea/edit{id}")
    fun edit(@Path("id") id: Long, @Body tarea: Tarea): Call<Tarea>

    @POST("tarea/get{id}")
    fun get(@Path("id") id: Long): Call<Tarea>

    @POST("tarea/remove{id}")
    fun remove(@Path("id") id: Long): Call<Void>
}