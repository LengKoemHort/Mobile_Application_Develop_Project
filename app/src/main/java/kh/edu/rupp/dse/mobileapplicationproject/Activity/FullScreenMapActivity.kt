package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityFullScreenMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.io.IOException
import java.util.*

class FullScreenMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenMapBinding
    private lateinit var map: MapView
    private var marker: Marker? = null
    private var pinnedLocation: GeoPoint? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize OSMDroid
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))

        map = binding.map
        map.setMultiTouchControls(true)

        val mapController = map.controller
        mapController.setZoom(15.0)

        // Center the map on RUPP
        val startPoint = GeoPoint(11.5713, 104.8990) // RUPP, Phnom Penh, Cambodia
        mapController.setCenter(startPoint)

        // Set up click listener to add marker and get address
        map.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                p?.let {
                    // Remove previous marker
                    marker?.let { m ->
                        map.overlays.remove(m)
                        map.invalidate()
                    }

                    // Add new marker
                    marker = Marker(map)
                    marker!!.position = it
                    marker!!.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    map.overlays.add(marker)
                    map.invalidate()
                    pinnedLocation = it
                    getAddress(it.latitude, it.longitude)?.let { address ->
                        Toast.makeText(this@FullScreenMapActivity, "Address: $address", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                // Not used
                return false
            }
        }))

        binding.closeFullscreenBtn.setOnClickListener {
            val resultIntent = Intent()
            pinnedLocation?.let {
                resultIntent.putExtra("latitude", it.latitude)
                resultIntent.putExtra("longitude", it.longitude)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.getAddressLine(0)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
