package com.example.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.adapter.ContactoAdapter
import com.example.agenda.adapter.EventoAdapter
import com.example.agenda.clients.Clients
import com.example.agenda.model.Contacto
import com.example.agenda.model.Evento
import com.example.agenda.service.ContactoService
import com.example.agenda.service.EventoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Eventos: Fragment() {

    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eventos, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = Clients.instance(view.context).create(EventoService::class.java)

        api.getAll().enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { listaEventos ->
                        val rv: RecyclerView = view.findViewById(R.id.rvEventos)
                        rv.layoutManager = LinearLayoutManager(view.context)

                        val adapter = EventoAdapter(
                            listaEventos,
                            onEditarClick = { evento ->
                                val fragment =  EventosFragment()
                                val bundle = Bundle().apply {
                                    evento.id?.let { putString("EVENTO_ID", it.toString()) }
                                }
                                fragment.arguments = bundle
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack(null)
                                    .commit()
                            },
                            onEliminarClick = { evento ->
                                evento.id?.let { id ->
                                    api.remove(id).enqueue(object : Callback<Void> {
                                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                            Toast.makeText(view.context, "Evento eliminado", Toast.LENGTH_SHORT).show()
                                            // Recargar el fragmento para actualizar la lista
                                            parentFragmentManager.beginTransaction()
                                                .detach(this@Eventos)
                                                .attach(this@Eventos)
                                                .commit()
                                        }

                                        override fun onFailure(call: Call<Void>, t: Throwable) {
                                            Toast.makeText(view.context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }
                            }
                        )

                        rv.adapter = adapter
                    }
                }

                val btnAgregar: FloatingActionButton = view.findViewById(R.id.addEvento)
                btnAgregar.setOnClickListener{

                    activity?.supportFragmentManager?.beginTransaction()!!
                        .replace(R.id.fragment_container, EventosFragment()).commit()
                }

            }


            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Toast.makeText(view.context, "Error al cargar eventos", Toast.LENGTH_SHORT).show()
            }
        })


    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Contactos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
