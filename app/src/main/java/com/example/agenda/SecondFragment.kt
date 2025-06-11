package com.example.agenda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.adapter.TareaAdapter
import com.example.agenda.clients.Clients
import com.example.agenda.databinding.FragmentSecondBinding
import com.example.agenda.model.Tarea
import com.example.agenda.service.Tareaservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var tareaId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(null != tareaId && tareaId != 0L) {

            val api = Clients.instance(view.context).create(Tareaservice::class.java)
            api.get(tareaId!!).enqueue(object : Callback<Tarea> {
                override fun onResponse(p0: Call<Tarea>, reponse: Response<Tarea>) {

                    if (reponse.isSuccessful){
                        val tarea = reponse.body()

                        val lblDescripcion: TextView = view.findViewById<TextView>(R.id.tuDescripcion)
                        lblDescripcion.apply {
                            text = tarea?.descripcion
                        }
                    }
                }

                override fun onFailure(p0: Call<Tarea>, p1: Throwable) {

                }
            })
            api.getAlL().enqueue(object : Callback<java.util.List<Tarea>> {
                override fun onResponse(call: Call<java.util.List<Tarea>>, response: Response<java.util.List<Tarea>>) {

                    if (response.isSuccessful) {

                        var tareas: java.util.List<Tarea>

                        response.body()?.let { t ->
                            tareas = t

                            val rv: RecyclerView = view.findViewById(R.id.rv)
                            rv.layoutManager = LinearLayoutManager(view.context)

                            val adapter = TareaAdapter(tareas)
                            rv.adapter = adapter

                        }
                        /*
                    response.body()?.let { tareas ->
                        for (tarea in tareas) {
                            Log.d("tarea", "ID: ${tarea.id}, Nombre: ${tarea.nombre}")
                        }
                    }*/
                    }
                }

                override fun onFailure(call: Call<java.util.List<Tarea>>, t: Throwable) {
                    Log.e("API_ERROR", "Error al obtener tareas", t)
                }
            })
        }
       // binding.buttonSecond.setOnClickListener {
         //   findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        //}

        binding.addTarea.setOnClickListener{

            activity?.supportFragmentManager?.beginTransaction()!!
                .replace(R.id.fragment_container, ContactoFragment()).commit()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}