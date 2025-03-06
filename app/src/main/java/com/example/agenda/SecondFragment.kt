package com.example.agenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.util.Log
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = Clients.instance(view.context).create(Tareaservice::class.java)
        api.getAlL().enqueue(object : Callback<List<Tarea>> {
            override fun onResponse(call: Call<List<Tarea>>, response: Response<List<Tarea>>) {
                if (response.isSuccessful) {

                    var tareas : List<Tarea> = ArrayList()

                    response.body()?.let { t ->
                        tareas = t
                            val rv : RecyclerView = view.findViewById(R.id.rv)
                            rv.layoutManager = LinearLayoutManager(view.context)

                            val adapter = TareaAdapter(tareas)
                            rv.adapter = adapter



                    }
                }
            }

            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                android.util.Log.e("Error", "Error en la API: ${t.message}")
            }


        })

        binding.AddTarea.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}