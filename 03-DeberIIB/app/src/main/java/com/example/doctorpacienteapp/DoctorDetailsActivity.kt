package com.example.doctorpacienteapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorpacienteapp.data.Doctor

class DoctorDetailsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var doctorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        val nameEditText: EditText = findViewById(R.id.doctorNameEditText)
        val saveButton: Button = findViewById(R.id.saveDoctorButton)

        databaseHelper = DatabaseHelper(this)
        doctorId = intent.getIntExtra("doctorId", -1)

        if (doctorId != -1) {
            val doctor = databaseHelper.getDoctorById(doctorId)
            doctor?.let { nameEditText.setText(it.name) }
        }

        saveButton.setOnClickListener {
            val doctorName = nameEditText.text.toString()
            if (doctorName.isNotEmpty()) {
                val doctor = Doctor(doctorId, doctorName)
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
}
