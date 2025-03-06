package com.example.agenda.util

import android.content.Context
import java.util.Properties

class Properties {

    companion object {

        fun getProperty(value : String, context: Context) : String {
            val properties = Properties()
            val inputStream = context.assets.open("config.properties")
            properties.load (inputStream)
            return properties.getProperty(value)
        }
    }
}