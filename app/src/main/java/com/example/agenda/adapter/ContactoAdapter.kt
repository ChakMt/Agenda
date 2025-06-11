package com.example.agenda.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.R
import com.example.agenda.SecondFragment
import com.example.agenda.model.Contacto

class ContactoAdapter(
    private val contacto: List<Contacto>,
    private val onEditarClick: (Contacto) -> Unit,
    private val onEliminarClick: (Contacto) -> Unit
) : RecyclerView.Adapter<ContactoAdapter.ContactoHolder>() {

    class ContactoHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val nombreContacto: TextView = view.findViewById(R.id.nombreContacto)
        private val telefono: TextView = view.findViewById(R.id.telefonoContacto)
        private val email: TextView = view.findViewById(R.id.email)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)

        private var contactoActual: Contacto = Contacto(0, "", "", "", "", "", "", "", "")

        init {
            view.setOnClickListener {
                val fragment = SecondFragment()
                val bundle = Bundle()
                contactoActual.id?.let {
                    bundle.putLong("CONTACTO_ID", it)
                }
                fragment.arguments = bundle
                val activity = view.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun render(contacto: Contacto) {
            contactoActual = contacto
            nombreContacto.text = contacto.nombre
            telefono.text = contacto.telefono
            email.text = contacto.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactoHolder(layoutInflater.inflate(R.layout.item_contacto, parent, false))
    }

    override fun getItemCount(): Int = contacto.size

    override fun onBindViewHolder(holder: ContactoHolder, position: Int) {
        val contactoItem = contacto[position]
        holder.render(contactoItem)

        holder.btnEditar.setOnClickListener {
            onEditarClick(contactoItem)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(contactoItem)
        }
    }
}
