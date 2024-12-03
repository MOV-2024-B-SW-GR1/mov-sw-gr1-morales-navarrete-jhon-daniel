package com.example
import java.time.LocalDate

data class Paciente(
    val id: Int,
    var nombre: String,
    var fechaNacimiento: LocalDate,
    var diagnostico: String,
    var telefono: Int,
    var deuda: Double
)