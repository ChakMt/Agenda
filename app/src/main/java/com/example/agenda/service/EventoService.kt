package com.example.agenda.service

import com.example.agenda.model.Contacto
import com.example.agenda.model.Evento;
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EventoService {

    @POST("evento/")
    fun getAll(): Call<List<Evento>>

    @POST("evento/add")
    fun  add(@Body evento: Evento): Call<Evento>

    @POST("evento/edit/{id}")
    fun edit(@Path("id") id: Long, @Body evento: Evento): Call<Evento>

    @POST("evento/get/{id}")
    fun get(@Path("id") id: Long): Call<Evento>

    @POST("evento/remove/{id}")
    fun remove(@Path("id") id: Long): Call<Void>

}