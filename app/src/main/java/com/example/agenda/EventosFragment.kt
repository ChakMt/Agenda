package com.example.agenda

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.agenda.clients.Clients
import com.example.agenda.databinding.FragmentFirstBinding
import com.example.agenda.model.Contacto
import com.example.agenda.model.Evento
import com.example.agenda.model.Tarea
import com.example.agenda.service.ContactoService
import com.example.agenda.service.EventoService
import com.example.agenda.service.Tareaservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EventosFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFirstBinding? = null
    private var eventoId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("EVENTO_ID")
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("TAG", "onViewCreated: "+ param1)

        val titulo : TextView =view.findViewById(R.id.tituloEvento)
        val descripcion : TextView =view.findViewById(R.id.descripcionDelEvento)
        val fechaIn : TextView =view.findViewById(R.id.fechaInEvento)
        val fechaFin : TextView =view.findViewById(R.id.fechaFinEvento)
        val ubicacion : TextView =view.findViewById(R.id.ubicacionEvento)
        val participante : TextView =view.findViewById(R.id.participanteEvento)
        //val participantesAdicionales : TextView =view.findViewById(R.id.participantesAdicionales)

        if (param1 != null) {

            val api = Clients.instance(view.context).create(EventoService::class.java)
            val evento : Long = param1!!.toLong()

            api.get(evento).enqueue(object : Callback<Evento> {

                override fun onResponse(call: Call<Evento>, response: Response<Evento>){

                    if (response.isSuccessful){

                        val evnt = response.body()
                        titulo.apply {
                            text= evnt?.titulo
                        }
                        descripcion.apply {
                            text= evnt?.descripcionEvento
                        }
                        fechaIn.apply {
                            text= evnt?.fechaIn
                        }
                        fechaFin.apply {
                            text= evnt?.fechaFin
                        }
                        ubicacion.apply {
                            text= evnt?.ubicacion
                        }
                        participante.apply {
                            text= evnt?.participante
                        }


                    }
                }

                override fun onFailure(call: Call<Evento>, t: Throwable){
                    Log.e("Error","Error en la API: ${t.message}")
                }
            })
        }

        val contenedorCorreos = view.findViewById<LinearLayout>(R.id.correoAdicionalContacto)
        val btnAgregarCorreo = view.findViewById<Button>(R.id.btnAgregarCorreo)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarEvento)

        /*btnAgregarCorreo.setOnClickListener {

            val nuevoCorreo = EditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                hint = "Correo electrónico"
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            contenedorCorreos.addView(nuevoCorreo)
        }*/

        btnGuardar.setOnClickListener() {

            if (param1 != null) {

                val eventoID : Long = param1!!.toLong()
                val api = Clients.instance(view.context).create(EventoService::class.java)
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
                var evento: Evento = Evento(
                    eventoID, titulo.text.toString(), "", fechaIn.text.toString(), fechaFin.text.toString(),
                    ubicacion.text.toString(), participante.text.toString(), ""
                )

                api.edit(eventoID, evento).enqueue(object : Callback<Evento> {

                    override fun onResponse(call: Call<Evento>, response: Response<Evento>) {

                        if (response.isSuccessful) {

                            Log.d("Evento", response.body().toString())

                            activity?.supportFragmentManager?.beginTransaction()!!
                                .replace(R.id.fragment_container, Eventos()).commit()

                        }
                    }

                    override fun onFailure(call: Call<Evento>, t: Throwable) {
                        Log.e("Error", "Error en la API: ${t.message}")
                    }
                })

            }else {


                val api = Clients.instance(view.context).create(EventoService::class.java)
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
                var evento: Evento = Evento(
                    null, titulo.text.toString(), "", fechaIn.text.toString(), fechaFin.text.toString(),
                    ubicacion.text.toString(), participante.text.toString(), ""
                )

                api.add(evento).enqueue(object : Callback<Evento> {

                    override fun onResponse(call: Call<Evento>, response: Response<Evento>) {

                        if (response.isSuccessful) {

                            Log.d("Evento", response.body().toString())

                            activity?.supportFragmentManager?.beginTransaction()!!
                                .replace(R.id.fragment_container, Eventos()).commit()

                        }
                    }

                    override fun onFailure(call: Call<Evento>, t: Throwable) {
                        Log.e("Error", "Error en la API: ${t.message}")
                    }
                })
                /* Toast.makeText(view.context, nombre.text, Toast.LENGTH_SHORT).show()*/
            }

        }





    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
