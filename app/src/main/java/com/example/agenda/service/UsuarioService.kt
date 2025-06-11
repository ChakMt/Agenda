package com.example.agenda.service

import com.example.agenda.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioService {
    @POST("usuario/")
    fun getAllUsuario(): Call<List<Usuario>>

    @POST("usuario/add")
    fun addUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("usuario/get/{id}")
    fun getUsuario(@Path("id")id: Long): Call<Usuario>

    @POST("usuario/recuperar-password")
    fun recuperarPassword(@Body usuario: Usuario): Call<Usuario>

    @POST("usuario/login")
    fun login(@Body usuario: Usuario): Call<Usuario>

}