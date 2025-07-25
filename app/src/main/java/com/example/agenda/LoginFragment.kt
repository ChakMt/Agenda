package com.example.agenda

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.agenda.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registrarseFragment)
        }

        binding.button2.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_olvideFragment)
        }

        binding.Aceptar.setOnClickListener{
            AlertDialog.Builder(requireContext()).setTitle("Bienvenido")
                .setMessage("Usuario encontrado")
                .setCancelable(false)
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener
                { dialog, id ->
                    val  intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                }).create().show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}