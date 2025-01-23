package com.example.doctorpacienteapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorpacienteapp.R
import com.example.doctorpacienteapp.data.Doctor

class DoctorAdapter(
    private val doctors: List<Doctor>,
    private val onEditClick: (Doctor) -> Unit,
    private val onDeleteClick: (Doctor) -> Unit,
    private val onViewPatientsClick: (Doctor) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        private val viewPatientsButton: ImageButton = itemView.findViewById(R.id.viewPatientsButton)

        fun bind(doctor: Doctor) {
            nameTextView.text = doctor.name
            editButton.setOnClickListener { onEditClick(doctor) }
            deleteButton.setOnClickListener { onDeleteClick(doctor) }
            viewPatientsButton.setOnClickListener { onViewPatientsClick(doctor) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(doctors[position])
    }

    override fun getItemCount(): Int = doctors.size
}
