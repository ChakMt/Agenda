package com.example.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.adapter.ContactoAdapter
import com.example.agenda.clients.Clients
import com.example.agenda.model.Contacto
import com.example.agenda.service.ContactoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Contactos : Fragment() {

    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("CONTACTO_ID")
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contactos, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = Clients.instance(view.context).create(ContactoService::class.java)

        api.getAll().enqueue(object : Callback<List<Contacto>> {
            override fun onResponse(call: Call<List<Contacto>>, response: Response<List<Contacto>>) {
                if (response.isSuccessful) {
                    response.body()?.let { listaContactos ->
                        val rv: RecyclerView = view.findViewById(R.id.rvContactos)
                        rv.layoutManager = LinearLayoutManager(view.context)

                        val adapter = ContactoAdapter(
                            listaContactos,
                            onEditarClick = { contacto ->
                                val fragment = ContactoFragment()
                                val bundle = Bundle().apply {
                                    contacto.id?.let { putString("CONTACTO_ID", it.toString()) }
                                }
                                fragment.arguments = bundle
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack(null)
                                    .commit()
                            },
                            onEliminarClick = { contacto ->
                                contacto.id?.let { id ->
                                    api.remove(id).enqueue(object : Callback<Void> {
                                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                            Toast.makeText(view.context, "Contacto eliminado", Toast.LENGTH_SHORT).show()
                                            // Recargar el fragmento para actualizar la lista
                                            parentFragmentManager.beginTransaction()
                                                .detach(this@Contactos)
                                                .attach(this@Contactos)
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

                val btnAgregar: FloatingActionButton = view.findViewById(R.id.addTarea)
                btnAgregar.setOnClickListener{

                    activity?.supportFragmentManager?.beginTransaction()!!
                        .replace(R.id.fragment_container, ContactoFragment()).commit()
                }

            }


            override fun onFailure(call: Call<List<Contacto>>, t: Throwable) {
                Toast.makeText(view.context, "Error al cargar contactos", Toast.LENGTH_SHORT).show()
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
