package com.example
import java.time.LocalDate

data class Doctor(
    val id: Int,
    var nombre: String,
    var especialidad: String,
    var fechaNacimiento: LocalDate,
    var salario: Double,
    var disponible: Boolean,
    val pacientes: MutableList<Paciente> = mutableListOf()
)
