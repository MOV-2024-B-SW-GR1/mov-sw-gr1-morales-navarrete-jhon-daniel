package com.example.doctorpacienteapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.Button
import android.widget.Toast

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var selectedLocation: LatLng? = null
    private lateinit var confirmLocationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        confirmLocationButton = findViewById(R.id.confirmLocationButton)

        val viewMode = intent.getBooleanExtra("viewMode", false)

        if (viewMode) {
            confirmLocationButton.visibility = View.GONE
        } else {
            confirmLocationButton.visibility = View.VISIBLE
        }

        confirmLocationButton.setOnClickListener {
            if (selectedLocation != null) {
                val resultIntent = Intent().apply {
                    putExtra("latitude", selectedLocation!!.latitude)
                    putExtra("longitude", selectedLocation!!.longitude)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "No se recibió una ubicación válida", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val viewMode = intent.getBooleanExtra("viewMode", false)

        if (latitude != 0.0 && longitude != 0.0) {
            val location = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(location).title("Ubicación guardada"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

            if (viewMode) {
                // Deshabilita interacciones si es solo visualización
                googleMap.uiSettings.isScrollGesturesEnabled = false
                googleMap.uiSettings.isZoomGesturesEnabled = false
            }
        }

        if (!viewMode) {
            // Permitir selección de nuevas ubicaciones en modo de asignación
            googleMap.setOnMapClickListener { latLng ->
                selectedLocation = latLng
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(latLng).title("Nueva ubicación"))
            }
        }
    }
}
