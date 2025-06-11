package com.example.agenda.clients

import android.content.Context
import com.example.agenda.util.Properties
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Clients{
    fun instance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Properties.getProperty("api.url", context))
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}