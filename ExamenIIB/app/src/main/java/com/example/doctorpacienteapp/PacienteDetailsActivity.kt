package com.example.doctorpacienteapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorpacienteapp.data.Paciente

class PacienteDetailsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var pacienteId: Int = -1
    private var doctorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente_details)

        val nameEditText: EditText = findViewById(R.id.pacienteNameEditText)
        val saveButton: Button = findViewById(R.id.savePacienteButton)
        val deleteButton: Button = findViewById(R.id.deletePacienteButton)
        val cancelButton: Button = findViewById(R.id.cancelPacienteButton)

        databaseHelper = DatabaseHelper(this)

        // Obtén los IDs de doctor y paciente
        doctorId = intent.getIntExtra("doctorId", -1)
        pacienteId = intent.getIntExtra("pacienteId", -1)

        // Si hay un paciente, carga sus datos
        if (pacienteId != -1) {
            val paciente = databaseHelper.getPacienteById(pacienteId)
            if (paciente != null) {
                nameEditText.setText(paciente.name)
            } else {
                Toast.makeText(this, "Paciente no encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Botón Guardar
        saveButton.setOnClickListener {
            val pacienteName = nameEditText.text.toString()
            if (pacienteName.isNotEmpty()) {
                if (pacienteId == -1) {
                    // Agregar nuevo paciente
                    val newId = databaseHelper.addPaciente(Paciente(0, pacienteName, doctorId))
                    if (newId != -1L) {
                        Toast.makeText(this, "Paciente añadido correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al añadir paciente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Actualizar paciente existente
                    val updatedRows = databaseHelper.updatePaciente(Paciente(pacienteId, pacienteName, doctorId))
                    if (updatedRows > 0) {
                        Toast.makeText(this, "Paciente actualizado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al actualizar paciente", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Eliminar
        deleteButton.setOnClickListener {
            if (pacienteId != -1) {
                val rowsDeleted = databaseHelper.deletePaciente(pacienteId)
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al eliminar paciente", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Botón Cancelar
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
