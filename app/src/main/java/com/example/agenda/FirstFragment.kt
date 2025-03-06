package com.example.agenda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.agenda.clients.Clients
import com.example.agenda.databinding.FragmentFirstBinding
import com.example.agenda.model.Tarea
import com.example.agenda.service.Tareaservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.jvm.Throws

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        var tarea : Tarea = Tarea(null, date,"Carlos", "algo", 1)



        val api = Clients.instance(view.context).create(Tareaservice::class.java)

        api.add(tarea).enqueue(object : Callback<Tarea>{

            override fun onResponse(call: Call <Tarea>, response: Response<Tarea>){

                if (response.isSuccessful){

                    Log.d("Tarea", response.body().toString())
                }
            }

            override fun onFailure(call: Call <Tarea>, t: Throwable){
                Log.e("Error","Error en la API: ${t.message}")
            }
        })



            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                Log.e("API_ERROR", "Error al obtener tareas", t)
            }
        })


        */
        binding.aceptar.setOnClickListener {
            val nombre: TextView = view.findViewById<TextView>(R.id.nombre)
            val prioridad: TextView = view.findViewById<TextView>(R.id.prioridad)
            val descripcion: TextView = view.findViewById<TextView>(R.id.descripcion)

            val api = Clients.instance(view.context).create(Tareaservice::class.java)
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            val tarea: Tarea = Tarea(
                null,
                date,
                nombre.text.toString(),
                descripcion.text.toString(),
                prioridad.text.toString().toInt()
            )

            api.add(tarea).enqueue(object : Callback<Tarea> {

                override fun onResponse(call: Call<Tarea>, response: Response<Tarea>) {

                    if (response.isSuccessful) {

                        Log.d("Tarea", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Tarea>, t: Throwable) {
                    Log.e("Error", "Error en la API: ${t.message}")
                }
            })


        }

        binding.verTodos.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }
}