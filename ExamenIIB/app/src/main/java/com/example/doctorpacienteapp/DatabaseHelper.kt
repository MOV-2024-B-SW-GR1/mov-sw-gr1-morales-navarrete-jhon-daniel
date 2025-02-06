package com.example.doctorpacienteapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.doctorpacienteapp.data.Doctor
import com.example.doctorpacienteapp.data.Paciente

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "DoctorPaciente.db"
        const val DATABASE_VERSION = 2 // Incrementamos la versión para agregar las columnas de latitud y longitud

        const val TABLE_DOCTOR = "Doctor"
        const val COLUMN_DOCTOR_ID = "id"
        const val COLUMN_DOCTOR_NAME = "name"
        const val COLUMN_DOCTOR_LATITUDE = "latitude"
        const val COLUMN_DOCTOR_LONGITUDE = "longitude"

        const val TABLE_PACIENTE = "Paciente"
        const val COLUMN_PACIENTE_ID = "id"
        const val COLUMN_PACIENTE_NAME = "name"
        const val COLUMN_PACIENTE_DOCTOR_ID = "doctor_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla Doctor
        db.execSQL(
            "CREATE TABLE $TABLE_DOCTOR (" +
                    "$COLUMN_DOCTOR_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_DOCTOR_NAME TEXT NOT NULL, " +
                    "$COLUMN_DOCTOR_LATITUDE REAL, " +
                    "$COLUMN_DOCTOR_LONGITUDE REAL)"
        )

        // Crear tabla Paciente
        db.execSQL(
            "CREATE TABLE $TABLE_PACIENTE (" +
                    "$COLUMN_PACIENTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_PACIENTE_NAME TEXT NOT NULL, " +
                    "$COLUMN_PACIENTE_DOCTOR_ID INTEGER NOT NULL, " +
                    "FOREIGN KEY ($COLUMN_PACIENTE_DOCTOR_ID) REFERENCES $TABLE_DOCTOR($COLUMN_DOCTOR_ID))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_DOCTOR ADD COLUMN $COLUMN_DOCTOR_LATITUDE REAL DEFAULT 0")
            db.execSQL("ALTER TABLE $TABLE_DOCTOR ADD COLUMN $COLUMN_DOCTOR_LONGITUDE REAL DEFAULT 0")
        }
    }

    fun addDoctor(doctor: Doctor): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DOCTOR_NAME, doctor.name)
            put(COLUMN_DOCTOR_LATITUDE, doctor.latitude)
            put(COLUMN_DOCTOR_LONGITUDE, doctor.longitude)
        }
        val id = db.insert(TABLE_DOCTOR, null, values)
        db.close()
        return id
    }

    fun updateDoctor(doctor: Doctor): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DOCTOR_NAME, doctor.name)
            put(COLUMN_DOCTOR_LATITUDE, doctor.latitude)
            put(COLUMN_DOCTOR_LONGITUDE, doctor.longitude)
        }
        val rows = db.update(TABLE_DOCTOR, values, "$COLUMN_DOCTOR_ID = ?", arrayOf(doctor.id.toString()))
        db.close()
        return rows
    }

    fun deleteDoctor(doctorId: Int): Int {
        val db = writableDatabase
        val rows = db.delete(TABLE_DOCTOR, "$COLUMN_DOCTOR_ID = ?", arrayOf(doctorId.toString()))
        db.close()
        return rows
    }

    fun getDoctorById(doctorId: Int): Doctor? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DOCTOR,
            arrayOf(COLUMN_DOCTOR_ID, COLUMN_DOCTOR_NAME, COLUMN_DOCTOR_LATITUDE, COLUMN_DOCTOR_LONGITUDE),
            "$COLUMN_DOCTOR_ID = ?",
            arrayOf(doctorId.toString()),
            null, null, null
        )
        val doctor = if (cursor.moveToFirst()) {
            Doctor(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3)
            )
        } else null
        cursor.close()
        db.close()
        return doctor
    }

    fun getAllDoctors(): List<Doctor> {
        val doctors = mutableListOf<Doctor>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DOCTOR, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val doctor = Doctor(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_NAME)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_LATITUDE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_LONGITUDE))
            )
            doctors.add(doctor)
        }
        cursor.close()
        db.close()
        return doctors
    }

    fun updateDoctorLocation(doctorId: Int, latitude: Double, longitude: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DOCTOR_LATITUDE, latitude)
            put(COLUMN_DOCTOR_LONGITUDE, longitude)
        }
        db.update(TABLE_DOCTOR, values, "$COLUMN_DOCTOR_ID = ?", arrayOf(doctorId.toString()))
        db.close()
    }

    fun getDoctorLocation(doctorId: Int): Pair<Double, Double>? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DOCTOR,
            arrayOf(COLUMN_DOCTOR_LATITUDE, COLUMN_DOCTOR_LONGITUDE),
            "$COLUMN_DOCTOR_ID = ?",
            arrayOf(doctorId.toString()),
            null,
            null,
            null
        )
        val location = if (cursor.moveToFirst()) {
            val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_LATITUDE))
            val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_LONGITUDE))
            Pair(latitude, longitude)
        } else null
        cursor.close()
        db.close()
        return location
    }

    fun addPaciente(paciente: Paciente): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PACIENTE_NAME, paciente.name)
            put(COLUMN_PACIENTE_DOCTOR_ID, paciente.doctorId)
        }
        val id = db.insert(TABLE_PACIENTE, null, values)
        db.close()
        return id
    }

    fun updatePaciente(paciente: Paciente): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PACIENTE_NAME, paciente.name)
            put(COLUMN_PACIENTE_DOCTOR_ID, paciente.doctorId)
        }
        val rowsUpdated = db.update(
            TABLE_PACIENTE,
            values,
            "$COLUMN_PACIENTE_ID = ?",
            arrayOf(paciente.id.toString())
        )
        db.close()
        return rowsUpdated
    }

    fun deletePaciente(pacienteId: Int): Int {
        val db = writableDatabase
        val rows = db.delete(TABLE_PACIENTE, "$COLUMN_PACIENTE_ID = ?", arrayOf(pacienteId.toString()))
        db.close()
        return rows
    }

    fun getPacienteById(pacienteId: Int): Paciente? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PACIENTE,
            arrayOf(COLUMN_PACIENTE_ID, COLUMN_PACIENTE_NAME, COLUMN_PACIENTE_DOCTOR_ID),
            "$COLUMN_PACIENTE_ID = ?",
            arrayOf(pacienteId.toString()),
            null, null, null
        )
        val paciente = if (cursor.moveToFirst()) {
            Paciente(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)
            )
        } else null
        cursor.close()
        db.close()
        return paciente
    }

    fun getPacientesByDoctorId(doctorId: Int): List<Paciente> {
        val pacientes = mutableListOf<Paciente>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PACIENTE,
            null,
            "$COLUMN_PACIENTE_DOCTOR_ID = ?",
            arrayOf(doctorId.toString()),
            null, null, null
        )
        while (cursor.moveToNext()) {
            val paciente = Paciente(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PACIENTE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PACIENTE_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PACIENTE_DOCTOR_ID))
            )
            pacientes.add(paciente)
        }
        cursor.close()
        db.close()
        return pacientes
    }
}
