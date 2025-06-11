package com.example.agenda.adapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.FirstFragment
import com.example.agenda.R
import com.example.agenda.SecondFragment
import com.example.agenda.model.Tarea

class TareaAdapter(private val tareas:java.util.List<Tarea>) : RecyclerView.Adapter<TareaAdapter.TareaHolder>() {

    class TareaHolder(val view: View) : RecyclerView.ViewHolder(view){
        private val tuNombre : TextView =view.findViewById(R.id.tuNombre)
        private val tuPrioridad : TextView =view.findViewById(R.id.tuPrioridad)
        private val tuDescripcion : TextView =view.findViewById(R.id.tuDescripcion)
        private var tareaActual: Tarea = Tarea(0, "", "", "", 0)

        init {
            view.setOnClickListener{

                val fragment = SecondFragment()
                val bundle = Bundle()
               tareaActual.id?.let { it1 ->
                   bundle.putLong("TAREAS_ID",it1)
               }
                fragment.arguments = bundle
                val activity = view.context as AppCompatActivity
                activity?.supportFragmentManager?.beginTransaction()!!
                    .replace(R.id.fragment_container, fragment).commit()

            }

        }

        fun render(tarea: Tarea) {
            tareaActual = tarea
            tuNombre.apply {
                text = tarea.nombre
            }
            tuDescripcion.apply {
                text = tarea.descripcion
            }
            tuPrioridad.apply {
                text = tarea.prioridad.toString()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TareaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return TareaHolder(layoutInflater.inflate(R.layout.item_tarea, parent, false))
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    override fun onBindViewHolder(holder: TareaHolder, position: Int) {
        holder.render(tareas[position])
    }


}