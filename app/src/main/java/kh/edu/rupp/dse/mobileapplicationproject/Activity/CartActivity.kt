package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.dse.mobileapplicationproject.Adapter.CartAdapter
import kh.edu.rupp.dse.mobileapplicationproject.Helper.ChangeNumberItemsListener
import kh.edu.rupp.dse.mobileapplicationproject.Helperimport.ManagementCart
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityCartBinding
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.io.IOException
import java.util.*

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var managementCart: ManagementCart
    private lateinit var map: MapView
    private var marker: Marker? = null
    private val REQUEST_CODE_FULL_SCREEN_MAP = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

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
                    getAddress(it.latitude, it.longitude)?.let { address ->
                        Toast.makeText(this@CartActivity, "Address: $address", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                // Not used
                return false
            }
        }))

        binding.fullscreenBtn.setOnClickListener {
            val intent = Intent(this, FullScreenMapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_FULL_SCREEN_MAP)
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()  // Handle back button click
        }

        setVariable()
        calculateCart()
        initList()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FULL_SCREEN_MAP && resultCode == RESULT_OK) {
            val latitude = data?.getDoubleExtra("latitude", 0.0)
            val longitude = data?.getDoubleExtra("longitude", 0.0)
            if (latitude != null && longitude != null && latitude != 0.0 && longitude != 0.0) {
                val geoPoint = GeoPoint(latitude, longitude)
                // Update the map with the new marker
                marker?.let { m ->
                    map.overlays.remove(m)
                    map.invalidate()
                }

                marker = Marker(map)
                marker!!.position = geoPoint
                marker!!.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                map.overlays.add(marker)
                map.invalidate()
                getAddress(latitude, longitude)?.let { address ->
                    Toast.makeText(this, "Address: $address", Toast.LENGTH_SHORT).show()
                }
                // Center the map on the new marker
                map.controller.setCenter(geoPoint)
            }
        }
    }

    private fun initList() {
        if (managementCart.getListCart().isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.scrollviewCart.visibility = View.GONE
        } else {
            binding.emptyTxt.visibility = View.GONE
            binding.scrollviewCart.visibility = View.VISIBLE
        }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cartView.layoutManager = linearLayoutManager
        adapter = CartAdapter(managementCart.getListCart(), this, object : ChangeNumberItemsListener {
            override fun change() {
                calculateCart()
            }
        })
        binding.cartView.adapter = adapter
    }

    private fun calculateCart() {
        val percentTax = 0.01
        val delivery = 2.0
        val tax = Math.round(managementCart.getTotalFee() * percentTax * 100.0) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0
        val itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0

        binding.totalFeeTxt.text = "$$itemTotal"
        binding.taxTxt.text = "$$tax"
        binding.deliveryTxt.text = "$$delivery"
        binding.totalTxt.text = "$$total"
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

    private fun setVariable() {
        // Initialize variables here
    }
}
