package com.example.agenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.R
import com.example.agenda.model.Contacto

class ContactoAdapter(private val contactos: List<Contacto>) : RecyclerView.Adapter<ContactoAdapter.ContactoHolder>() {

    class ContactoHolder(val view: View) : RecyclerView.ViewHolder(view){
        private val tuNombre: TextView = view.findViewById(R.id.tuNombre)
        private val tuTelefono: TextView = view.findViewById(R.id.tuTelefono)
        private val tuFechaNacimiento: TextView = view.findViewById(R.id.tuFechaNacimiento)
        private val tuCorreo: TextView = view.findViewById(R.id.tuCorreo)
        private var contactoActual: Contacto = Contacto(0, "", "", "", "")

        init {
            view.setOnClickListener(){
                Toast.makeText(view.context, contactoActual.nombre, Toast.LENGTH_SHORT).show()
            }
        }

        fun render(contacto: Contacto){
            contactoActual = contacto
            tuNombre.apply {
                text = contacto.nombre
            }
            tuTelefono.apply {
                text = contacto.telefono
            }
            tuFechaNacimiento.apply {
                text = contacto.fechaNacimiento
            }
            tuCorreo.apply {
                text = contacto.correo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ContactoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactoHolder(layoutInflater.inflate(R.layout.item_contacto, parent, false))
    }

    override fun getItemCount(): Int {
        return contactos.size
    }

    override fun onBindViewHolder(holder: ContactoHolder, position: Int) {
        holder.render(contactos[position])
    }
}
