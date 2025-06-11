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
import com.example.agenda.model.Evento

class EventoAdapter(
    private val evento: List<Evento>,
    private val onEditarClick: (Evento) -> Unit,
    private val onEliminarClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoHolder>() {

    class EventoHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val titulo: TextView = view.findViewById(R.id.titulo)
        private val descripcion: TextView = view.findViewById(R.id.descripcionEvento)
        private val fechaIn: TextView = view.findViewById(R.id.fechaIn)
        private val fechaFin: TextView = view.findViewById(R.id.fechaFin)
        private val ubicacion: TextView = view.findViewById(R.id.ubicacion)
        private val participante: TextView = view.findViewById(R.id.participante)
        private val participantesAdicionales: TextView = view.findViewById(R.id.participantesAdicionales)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)

        private var eventoActual: Evento = Evento(0, "", "", "", "", "", "", "")


        init {
            view.setOnClickListener {
                val fragment = SecondFragment()
                val bundle = Bundle()
                eventoActual.id?.let {
                    bundle.putLong("EVENTO_ID", it)
                }
                fragment.arguments = bundle
                val activity = view.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun render(evento: Evento) {
            eventoActual = evento
            titulo.text = evento.titulo
            descripcion.text = evento.descripcionEvento
            fechaIn.text = evento.fechaIn
            fechaFin.text = evento.fechaFin
            ubicacion.text = evento.ubicacion
            participante.text = evento.participante
            participantesAdicionales.text = evento.participantesAdicionales
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventoHolder(layoutInflater.inflate(R.layout.item_evento, parent, false))
    }

    override fun getItemCount(): Int = evento.size

    override fun onBindViewHolder(holder: EventoHolder, position: Int) {
        val eventoItem = evento[position]
        holder.render(eventoItem)

        holder.btnEditar.setOnClickListener {
            onEditarClick(eventoItem)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(eventoItem)
        }
    }
}
