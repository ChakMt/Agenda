package com.example.agenda

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.agenda.clients.Clients
import com.example.agenda.model.Usuario
import com.example.agenda.service.UsuarioService
import com.example.agenda.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OlvideContrasenaFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_olvide_contrasena, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = Clients.instance(view.context).create(UsuarioService::class.java)
        val email = view.findViewById<TextView>(R.id.username)
        val nueva_contrasenaText = view.findViewById<TextView>(R.id.nueva_contrasena)
        val confirmar_nueva_contrasenaText = view.findViewById<TextView>(R.id.confirmar_nueva_contrasena)
        val btnConfirmar = view.findViewById<TextView>(R.id.btn_confirmar_contrasena)

        val nueva_contrasena = nueva_contrasenaText.text.toString()
        val confirmar_nueva_contrasena = confirmar_nueva_contrasenaText.text.toString()

        btnConfirmar.setOnClickListener {


            if(nueva_contrasenaText.text.toString() == confirmar_nueva_contrasenaText.text.toString()) {
                val usuario: Usuario = Usuario(null, null, null,
                    null, email.text.toString(), nueva_contrasenaText.text.toString(),
                    null, null)
                api.recuperarPassword(usuario).enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if(response.isSuccessful && null != response.body()){
                            val usuarioResponse = response.body();
                            var mensaje = "La contraseña se cambio exitosamente"
                            if (usuarioResponse?.code != "200") {
                                mensaje = usuarioResponse?.mensaje.toString()
                            }

                            AlertDialog.Builder(requireContext()).setTitle("Cambio de contraseña")
                                .setMessage(mensaje)
                                .setCancelable(false)
                                .setPositiveButton("Aceptar", DialogInterface.OnClickListener
                                { dialog, id ->
                                    if (usuarioResponse?.code == "200") {
                                        val  intent = Intent(view.context, LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                }).create().show()
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, response: Throwable) {

                    }
                })

            }else {
                AlertDialog.Builder(requireContext()).setTitle("Contraseña")
                    .setMessage("Las contraseñas no coinciden")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id -> })
                    .create().show()

            }

        }

    }

}