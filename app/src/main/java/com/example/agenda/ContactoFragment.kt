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

private const val ARG_PARAM1 = "CONTACTO_ID"
private const val ARG_PARAM2 = "param2"

class ContactoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFirstBinding? = null
    private var contactoId: Long? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("TAG", "onViewCreated: "+ param1)

        val nombre : TextView =view.findViewById(R.id.nombreContacto)
        val apellidoMa : TextView =view.findViewById(R.id.apellidoMaContacto)
        val apellidoPa : TextView =view.findViewById(R.id.apellidoPaContacto)
        val telefono : TextView =view.findViewById(R.id.telefonoContacto)
        val email : TextView =view.findViewById(R.id.correoContacto)
        val direccion : TextView =view.findViewById(R.id.direccionContacto)
        val telefonoAdicional : TextView =view.findViewById(R.id.telefonoAdicionalContacto)
        //val emailAdicional : TextView =view.findViewById(R.id.correoAdicionalContacto)

        if (param1 != null) {

            val api = Clients.instance(view.context).create(ContactoService::class.java)
            val contacto : Long = param1!!.toLong()

            api.get(contacto).enqueue(object : Callback<Contacto> {

                override fun onResponse(call: Call<Contacto>, response: Response<Contacto>){

                    if (response.isSuccessful){

                        val evnt = response.body()
                        nombre.apply {
                            text= evnt?.nombre
                        }
                        apellidoMa.apply {
                            text= evnt?.apellidoMa
                        }
                        apellidoPa.apply {
                            text= evnt?.apellidoPa
                        }
                        telefono.apply {
                            text= evnt?.telefono
                        }
                        email.apply {
                            text= evnt?.email
                        }
                        direccion.apply {
                            text= evnt?.direccion
                        }
                        telefonoAdicional.apply {
                            text= evnt?.telefonoAdicional
                        }


                    }
                }

                override fun onFailure(call: Call<Contacto>, t: Throwable){
                    Log.e("Error","Error en la API: ${t.message}")
                }
            })
        }

        val contenedorCorreos = view.findViewById<LinearLayout>(R.id.correoAdicionalContacto)
        val btnAgregarCorreo = view.findViewById<Button>(R.id.btnAgregarCorreo)
        val btnGuardar = view.findViewById<Button>(R.id.buttonGuardar)



        btnAgregarCorreo.setOnClickListener {

            val nuevoCorreo = EditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                hint = "Correo electrónico"
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            contenedorCorreos.addView(nuevoCorreo)
        }
        btnGuardar.setOnClickListener() {

            if (param1 != null) {

                val contactoID : Long = param1!!.toLong()
                val api = Clients.instance(view.context).create(ContactoService::class.java)
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
                var contacto: Contacto = Contacto(contactoID,
                    nombre.text.toString(),
                    apellidoMa.text.toString(),
                    apellidoPa.text.toString(),
                    telefono.text.toString(),
                    email.text.toString(),
                    direccion.text.toString(),
                    telefonoAdicional.text.toString(),
                    ""
                )

                api.edit(contactoID, contacto).enqueue(object : Callback<Contacto> {

                    override fun onResponse(call: Call<Contacto>, response: Response<Contacto>) {

                        if (response.isSuccessful) {

                            Log.d("Contacto", response.body().toString())

                            activity?.supportFragmentManager?.beginTransaction()!!
                                .replace(R.id.fragment_container, Contactos()).commit()

                        }
                    }

                    override fun onFailure(call: Call<Contacto>, t: Throwable) {
                        Log.e("Error", "Error en la API: ${t.message}")
                    }
                })
            }else{
                val api = Clients.instance(view.context).create(ContactoService::class.java)
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
                var contacto: Contacto = Contacto(
                    null,
                    nombre.text.toString(),
                    apellidoMa.text.toString(),
                    apellidoPa.text.toString(),
                    telefono.text.toString(),
                    email.text.toString(),
                    direccion.text.toString(),
                    telefonoAdicional.text.toString(),
                    ""
                )

                api.add(contacto).enqueue(object : Callback<Contacto> {

                    override fun onResponse(call: Call<Contacto>, response: Response<Contacto>) {

                        if (response.isSuccessful) {

                            Log.d("Contacto", response.body().toString())

                            activity?.supportFragmentManager?.beginTransaction()!!
                                .replace(R.id.fragment_container, Contactos()).commit()

                        }
                    }

                    override fun onFailure(call: Call<Contacto>, t: Throwable) {
                        Log.e("Error", "Error en la API: ${t.message}")
                    }
                })

            }

        }
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
