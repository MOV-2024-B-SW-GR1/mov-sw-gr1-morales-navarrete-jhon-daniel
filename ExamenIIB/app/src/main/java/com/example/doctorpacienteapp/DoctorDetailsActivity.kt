package com.example.doctorpacienteapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorpacienteapp.data.Doctor

class DoctorDetailsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var doctorId: Int = -1
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        val nameEditText: EditText = findViewById(R.id.doctorNameEditText)
        val saveButton: Button = findViewById(R.id.saveDoctorButton)
        val selectLocationButton: Button = findViewById(R.id.setDoctorLocationButton)

        databaseHelper = DatabaseHelper(this)
        doctorId = intent.getIntExtra("doctorId", -1)

        if (doctorId != -1) {
            val doctor = databaseHelper.getDoctorById(doctorId)
            doctor?.let {
                nameEditText.setText(it.name)
                latitude = it.latitude!!
                longitude = it.longitude!!
            }
        }

        // Abrir el mapa para seleccionar ubicación
        selectLocationButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivityForResult(intent, REQUEST_MAP)
        }

        // Guardar cambios en el doctor
        saveButton.setOnClickListener {
            val doctorName = nameEditText.text.toString()
            if (doctorName.isNotEmpty()) {
                val doctor = Doctor(doctorId, doctorName, latitude, longitude)
                if (doctorId == -1) {
                    databaseHelper.addDoctor(doctor)
                    Toast.makeText(this, "Doctor añadido", Toast.LENGTH_SHORT).show()
                } else {
                    databaseHelper.updateDoctor(doctor)
                    Toast.makeText(this, "Doctor actualizado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Recibir la ubicación desde MapActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_MAP && resultCode == Activity.RESULT_OK) {
            latitude = data?.getDoubleExtra("latitude", 0.0) ?: 0.0
            longitude = data?.getDoubleExtra("longitude", 0.0) ?: 0.0
            Toast.makeText(this, "Ubicación asignada correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_MAP = 100
    }
}
