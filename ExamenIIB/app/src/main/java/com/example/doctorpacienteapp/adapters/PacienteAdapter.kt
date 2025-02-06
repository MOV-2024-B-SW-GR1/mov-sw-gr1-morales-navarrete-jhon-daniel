package com.example.doctorpacienteapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorpacienteapp.R
import com.example.doctorpacienteapp.data.Paciente

class PacienteAdapter(
    private val pacientes: List<Paciente>,
    private val onEditClick: (Paciente) -> Unit,
    private val onDeleteClick: (Paciente) -> Unit
) : RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>() {

    inner class PacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(paciente: Paciente) {
            nameTextView.text = paciente.name
            editButton.setOnClickListener { onEditClick(paciente) }
            deleteButton.setOnClickListener { onDeleteClick(paciente) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paciente, parent, false)
        return PacienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        holder.bind(pacientes[position])
    }

    override fun getItemCount(): Int = pacientes.size
}
