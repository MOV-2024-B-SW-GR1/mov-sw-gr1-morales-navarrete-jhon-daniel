package com.example.doctorpacienteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
            onViewPatientsClick = { doctor -> viewPatients(doctor) },
            onViewLocationClick = { doctor -> openMap(doctor) }
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


    private fun openMap(doctor: Doctor) {
        val location = databaseHelper.getDoctorLocation(doctor.id)
        if (location != null) {
            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra("latitude", location.first)
                putExtra("longitude", location.second)
                putExtra("viewMode", true) // Modo visualización
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Ubicación no asignada para este doctor", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadDoctors()
    }
}
