package kh.edu.rupp.dse.mobileapplicationproject

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityMainBinding
import kh.edu.rupp.dse.mobileapplicationproject.domain.Location
import kh.edu.rupp.dse.mobileapplicationproject.domain.Price
import kh.edu.rupp.dse.mobileapplicationproject.domain.Time

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        initLocation()
        initTime()
        initPrice()
    }

    private fun initLocation() {
        val list = ArrayList<Location>()

        val myRef = databaseReference.child("Location")
        val adapter = ArrayAdapter(
            this@MainActivity,
            R.layout.sp_item,
            list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSp.adapter = adapter

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (issue in snapshot.children) {
                    issue.getValue(Location::class.java)?.let {
                        list.add(it)
                    }
                }
                adapter.notifyDataSetChanged() // Notify adapter after data change
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
    private fun initTime() {
        val list = ArrayList<Time>()

        val myRef = databaseReference.child("Time")
        val adapter = ArrayAdapter(
            this@MainActivity,
            R.layout.sp_item,
            list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timeSp.adapter = adapter

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (issue in snapshot.children) {
                    issue.getValue(Time::class.java)?.let {
                        list.add(it)
                    }
                }
                adapter.notifyDataSetChanged() // Notify adapter after data change
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
    private fun initPrice() {
        val list = ArrayList<Price>()

        val myRef = databaseReference.child("Price")
        val adapter = ArrayAdapter(
            this@MainActivity,
            R.layout.sp_item,
            list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.priceSp.adapter = adapter // assuming "priceSp" is the ID of your spinner for prices

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (issue in snapshot.children) {
                    issue.getValue(Price::class.java)?.let {
                        list.add(it)
                    }
                }
                adapter.notifyDataSetChanged() // Notify adapter after data change
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}
