package com.example

import java.io.File
import java.time.LocalDate

object FileManager {

    private val doctorFile = File("doctores.txt")
    fun guardarDoctores(doctores: List<Doctor>) {
        doctorFile.writeText(doctores.joinToString("\n") { doctorToString(it) })
    }

    fun cargarDoctores(): MutableList<Doctor> {
        if (!doctorFile.exists()) return mutableListOf()
        return doctorFile.readLines().map { stringToDoctor(it) }.toMutableList()
    }

    private fun doctorToString(doctor: Doctor): String {
        val pacientesString = doctor.pacientes.joinToString("|") { pacienteToString(it) }
        return "${doctor.id},${doctor.nombre},${doctor.especialidad},${doctor.fechaNacimiento},${doctor.salario},${doctor.disponible},[$pacientesString]"
    }

    private fun stringToDoctor(data: String): Doctor {
        val parts = data.split(",")
        val pacientesData = parts.last().removeSurrounding("[", "]")
        val pacientes = if (pacientesData.isBlank()) mutableListOf() else pacientesData.split("|").map { stringToPaciente(it) }.toMutableList()
        return Doctor(
            id = parts[0].toInt(),
            nombre = parts[1],
            especialidad = parts[2],
            fechaNacimiento = LocalDate.parse(parts[3]),
            salario = parts[4].toDouble(),
            disponible = parts[5].toBoolean(),
            pacientes = pacientes
        )
    }

    private fun pacienteToString(paciente: Paciente): String {
        return "${paciente.id},${paciente.nombre},${paciente.fechaNacimiento},${paciente.diagnostico},${paciente.telefono},${paciente.deuda}"
    }

    private fun stringToPaciente(data: String): Paciente {
        val parts = data.split(",")
        return Paciente(
            id = parts[0].toInt(),
            nombre = parts[1],
            fechaNacimiento = LocalDate.parse(parts[2]),
            diagnostico = parts[3],
            telefono = parts[4].toInt(),
            deuda = parts[5].toDouble()
        )
    }
}
