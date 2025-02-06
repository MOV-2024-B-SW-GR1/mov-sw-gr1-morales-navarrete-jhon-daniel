package com.example.doctorpacienteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorpacienteapp.adapters.PacienteAdapter
import com.example.doctorpacienteapp.data.Paciente

class PacienteListActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: PacienteAdapter
    private val pacientes = mutableListOf<Paciente>()
    private var doctorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente_list)

        doctorId = intent.getIntExtra("doctorId", -1)
        databaseHelper = DatabaseHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewPacientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PacienteAdapter(pacientes, { paciente -> editPaciente(paciente) }, { paciente -> deletePaciente(paciente) })
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.addPacienteButton).setOnClickListener {
            val intent = Intent(this, PacienteDetailsActivity::class.java)
            intent.putExtra("doctorId", doctorId)
            startActivity(intent)
        }
        loadPacientes()
    }

    private fun loadPacientes() {
        pacientes.clear()
        pacientes.addAll(databaseHelper.getPacientesByDoctorId(doctorId))
        adapter.notifyDataSetChanged()
    }

    private fun editPaciente(paciente: Paciente) {
        val intent = Intent(this, PacienteDetailsActivity::class.java)
        intent.putExtra("pacienteId", paciente.id)
        startActivity(intent)
    }

    private fun deletePaciente(paciente: Paciente) {
        databaseHelper.deletePaciente(paciente.id)
        loadPacientes()
    }

    override fun onResume() {
        super.onResume()
        loadPacientes()
    }
}
