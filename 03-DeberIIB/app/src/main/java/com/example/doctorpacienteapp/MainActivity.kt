package com.example.doctorpacienteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorpacienteapp.adapters.DoctorAdapter
import com.example.doctorpacienteapp.data.Doctor

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: DoctorAdapter
    private val doctors = mutableListOf<Doctor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewDoctors)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DoctorAdapter(
            doctors,
            onEditClick = { doctor -> openDoctorDetails(doctor) },
            onDeleteClick = { doctor -> deleteDoctor(doctor) },
            onViewPatientsClick = { doctor -> viewPatients(doctor) } // Solución: Añadir esta línea
        )
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.addDoctorButton).setOnClickListener {
            val intent = Intent(this, DoctorDetailsActivity::class.java)
            startActivity(intent)
        }

        loadDoctors()
    }

    private fun loadDoctors() {
        doctors.clear()
        doctors.addAll(databaseHelper.getAllDoctors())
        adapter.notifyDataSetChanged()
    }

    private fun openDoctorDetails(doctor: Doctor) {
        val intent = Intent(this, DoctorDetailsActivity::class.java)
        intent.putExtra("doctorId", doctor.id)
        startActivity(intent)
    }

    private fun deleteDoctor(doctor: Doctor) {
        databaseHelper.deleteDoctor(doctor.id)
        loadDoctors()
    }

    private fun viewPatients(doctor: Doctor) {
        val intent = Intent(this, PacienteListActivity::class.java)
        intent.putExtra("doctorId", doctor.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadDoctors()
    }
}
