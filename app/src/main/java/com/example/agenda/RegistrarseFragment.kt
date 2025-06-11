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
import com.example.agenda.service.Tareaservice
import com.example.agenda.service.UsuarioService
import com.example.agenda.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrarseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrarseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrarse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = Clients.instance(view.context).create(UsuarioService::class.java)
        val nombre = view.findViewById<TextView>(R.id.nombre)
        val apellidoPaterno = view.findViewById<TextView>(R.id.apellidoPaterno)
        val apellidoMaterno = view.findViewById<TextView>(R.id.apellidoMaterno)
        val email = view.findViewById<TextView>(R.id.email)
        val password = view.findViewById<TextView>(R.id.password)

        val button = view.findViewById<Button>(R.id.btn_aceptar)
        button.setOnClickListener {
            val usuario = Usuario(null, nombre.text.toString(), apellidoPaterno.text.toString(),
                apellidoMaterno.text.toString(), email.text.toString(), password.text.toString(),
                "", "")

            api.addUsuario(usuario).enqueue(object :Callback<Usuario>{

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        AlertDialog.Builder(requireContext()).setTitle("Usuario Registrado")
                        .setMessage("El usuario se registro exitosamente")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", DialogInterface.OnClickListener
                        { dialog, id ->
                        val  intent = Intent(view.context, LoginActivity::class.java)
                        startActivity(intent)
                        }).create().show()

                    }
                }
                override fun onFailure(call: Call<Usuario>, t: Throwable) {

                }
            })

        }
    }


}