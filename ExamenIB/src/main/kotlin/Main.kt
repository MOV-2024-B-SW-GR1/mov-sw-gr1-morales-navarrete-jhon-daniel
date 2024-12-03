package com.example

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    val doctores = FileManager.cargarDoctores()
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    while (true) {
        println("\n--- Sistema CRUD ---")
        println("1. Crear Doctor")
        println("2. Crear Paciente")
        println("3. Ver Doctores")
        println("4. Ver Pacientes de un Doctor")
        println("5. Actualizar Doctor")
        println("6. Actualizar Paciente")
        println("7. Eliminar Doctor")
        println("8. Eliminar Paciente")
        println("9. Salir")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Ingrese los datos del Doctor:")
                print("ID: ")
                val id = readLine()?.toIntOrNull() ?: continue
                print("Nombre: ")
                val nombre = readLine() ?: continue
                print("Especialidad: ")
                val especialidad = readLine() ?: continue
                print("Fecha de Nacimiento (yyyy-MM-dd): ")
                val fechaNacimiento = LocalDate.parse(readLine(), dateFormat)
                print("Salario: ")
                val salario = readLine()?.toDoubleOrNull() ?: continue
                print("Disponible (true/false): ")
                val disponible = readLine()?.toBooleanStrictOrNull() ?: continue

                doctores.add(Doctor(id, nombre, especialidad, fechaNacimiento, salario, disponible))
                FileManager.guardarDoctores(doctores)
                println("Doctor creado exitosamente.")
            }

            2 -> {
                println("Ingrese el ID del Doctor al que pertenece el paciente:")
                doctores.forEach { println("${it.id} - ${it.nombre}") }
                val doctorId = readLine()?.toIntOrNull()
                val doctor = doctores.find { it.id == doctorId }
                if (doctor == null) {
                    println("Doctor no encontrado.")
                    continue
                }

                println("Ingrese los datos del Paciente:")
                print("ID: ")
                val id = readLine()?.toIntOrNull() ?: continue
                print("Nombre: ")
                val nombre = readLine() ?: continue
                print("Fecha de Nacimiento (yyyy-MM-dd): ")
                val fechaNacimiento = LocalDate.parse(readLine(), dateFormat)
                print("Diagnóstico: ")
                val diagnostico = readLine() ?: continue
                print("Teléfono: ")
                val telefono = readLine()?.toIntOrNull() ?: continue
                print("Deuda: ")
                val deuda = readLine()?.toDoubleOrNull() ?: continue

                doctor.pacientes.add(Paciente(id, nombre, fechaNacimiento, diagnostico, telefono, deuda))
                FileManager.guardarDoctores(doctores)
                println("Paciente creado exitosamente.")
            }

            3 -> {
                println("--- Doctores ---")
                doctores.forEach { doctor ->
                    println("ID: ${doctor.id}, Nombre: ${doctor.nombre}, Especialidad: ${doctor.especialidad}, Disponible: ${doctor.disponible}")
                }
            }

            4 -> {
                println("Ingrese el ID del Doctor para ver sus pacientes:")
                doctores.forEach { println("${it.id} - ${it.nombre}") }
                val doctorId = readLine()?.toIntOrNull()
                val doctor = doctores.find { it.id == doctorId }
                if (doctor == null) {
                    println("Doctor no encontrado.")
                    continue
                }

                println("--- Pacientes del Doctor ${doctor.nombre} ---")
                if (doctor.pacientes.isEmpty()) {
                    println("No tiene pacientes registrados.")
                } else {
                    doctor.pacientes.forEach { paciente ->
                        println("ID: ${paciente.id}, Nombre: ${paciente.nombre}, Diagnóstico: ${paciente.diagnostico}, Deuda: ${paciente.deuda}")
                    }
                }
            }

            5 -> {
                println("Ingrese el ID del Doctor que desea actualizar:")
                val id = readLine()?.toIntOrNull()
                val doctor = doctores.find { it.id == id }
                if (doctor == null) {
                    println("Doctor no encontrado.")
                    continue
                }

                print("Nuevo nombre (${doctor.nombre}): ")
                val nuevoNombre = readLine() ?: doctor.nombre
                doctor.nombre = nuevoNombre

                FileManager.guardarDoctores(doctores)
                println("Doctor actualizado exitosamente.")
            }

            6 -> {
                println("Ingrese el ID del Doctor al que pertenece el paciente:")
                doctores.forEach { println("${it.id} - ${it.nombre}") }
                val doctorId = readLine()?.toIntOrNull()
                val doctor = doctores.find { it.id == doctorId }
                if (doctor == null) {
                    println("Doctor no encontrado.")
                    continue
                }

                println("Ingrese el ID del Paciente que desea actualizar:")
                doctor.pacientes.forEach { println("${it.id} - ${it.nombre}") }
                val pacienteId = readLine()?.toIntOrNull()
                val paciente = doctor.pacientes.find { it.id == pacienteId }
                if (paciente == null) {
                    println("Paciente no encontrado.")
                    continue
                }

                print("Nuevo nombre (${paciente.nombre}): ")
                val nuevoNombre = readLine() ?: paciente.nombre
                paciente.nombre = nuevoNombre

                FileManager.guardarDoctores(doctores)
                println("Paciente actualizado exitosamente.")
            }

            7 -> {
                println("Ingrese el ID del Doctor que desea eliminar:")
                val id = readLine()?.toIntOrNull()
                if (doctores.removeIf { it.id == id }) {
                    FileManager.guardarDoctores(doctores)
                    println("Doctor eliminado exitosamente.")
                } else {
                    println("Doctor no encontrado.")
                }
            }

            8 -> {
                println("Ingrese el ID del Doctor al que pertenece el paciente:")
                doctores.forEach { println("${it.id} - ${it.nombre}") }
                val doctorId = readLine()?.toIntOrNull()
                val doctor = doctores.find { it.id == doctorId }
                if (doctor == null) {
                    println("Doctor no encontrado.")
                    continue
                }

                println("Ingrese el ID del Paciente que desea eliminar:")
                doctor.pacientes.forEach { println("${it.id} - ${it.nombre}") }
                val pacienteId = readLine()?.toIntOrNull()
                if (doctor.pacientes.removeIf { it.id == pacienteId }) {
                    FileManager.guardarDoctores(doctores)
                    println("Paciente eliminado exitosamente.")
                } else {
                    println("Paciente no encontrado.")
                }
            }

            9 -> {
                println("Saliendo del sistema...")
                break
            }

            else -> println("Opción inválida.")
        }
    }
}