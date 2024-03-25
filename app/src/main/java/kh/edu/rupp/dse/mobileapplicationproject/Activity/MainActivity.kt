package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kh.edu.rupp.dse.mobileapplicationproject.R
import kh.edu.rupp.dse.mobileapplicationproject.adapter.BestFoodAdapter
import kh.edu.rupp.dse.mobileapplicationproject.adapter.CategoryAdapter
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityMainBinding
import kh.edu.rupp.dse.mobileapplicationproject.domain.Category
import kh.edu.rupp.dse.mobileapplicationproject.domain.Foods
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
        initBestFood()
        initCategory()
    }

    private fun initCategory() {
        val myRef = databaseReference.child("Category")
        binding.progressBar2.visibility = View.VISIBLE
        val list = ArrayList<Category>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Category::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding.CategoryView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = CategoryAdapter(list)
                        binding.CategoryView.adapter = adapter
                    }
                    binding.progressBar2.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
                binding.progressBar2.visibility = View.GONE
            }
        })
    }

    private fun initBestFood() {
        val myRef = databaseReference.child("Foods")
        binding.progressBar1.visibility = View.VISIBLE
        val list = ArrayList<Foods>()
        val query: Query = myRef.orderByChild("BestFood").equalTo(true)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding.bestFoodView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = BestFoodAdapter(list)
                        binding.bestFoodView.adapter = adapter
                    }
                    binding.progressBar1.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
                binding.progressBar1.visibility = View.GONE
            }
        })
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
