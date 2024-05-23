package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kh.edu.rupp.dse.mobileapplicationproject.Adapter.BestFoodsAdapter
import kh.edu.rupp.dse.mobileapplicationproject.Adapter.CategoryAdapter
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Category
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Foods
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Location
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Price
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Time
import kh.edu.rupp.dse.mobileapplicationproject.R
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        initLocation()
        initTime()
        initPrice()
        initBestFood()
        initCategory()
        setVariable()
    }

    private fun setVariable() {
        binding.logoutBtn.setOnClickListener {
            // Handle logout
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.searchBtn.setOnClickListener {
            val text = binding.searchEdit.text.toString()
            if (text.isNotEmpty()) {
                val intent = Intent(this, ListFoodActivity::class.java)
                intent.putExtra("text", text)
                intent.putExtra("isSearch", true)
                startActivity(intent)
            }
        }
        binding.cardBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }


    private fun initCategory() {
        val myRef = databaseReference.child("Category")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<Category>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Category::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        // Use GridLayoutManager for category view
                        val layoutManager = GridLayoutManager(this@HomeActivity, 4)
                        binding.categoryView.layoutManager = layoutManager
                        val adapter = CategoryAdapter(list)
                        binding.categoryView.adapter = adapter
                    }
                    binding.progressBarCategory.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
                binding.progressBarCategory.visibility = View.GONE
            }
        })
    }


    private fun initBestFood() {
        val myRef = databaseReference.child("Foods")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<Foods>()
        val query: Query = myRef.orderByChild("BestFood").equalTo(true)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.size > 0) {
                        binding.bestFoodView.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = BestFoodsAdapter(list)
                        binding.bestFoodView.adapter = adapter
                    }
                    binding.progressBarBestFood.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
                binding.progressBarBestFood.visibility = View.GONE
            }
        })
    }

    private fun initLocation() {
        val myRef = databaseReference.child("Location")
        val list = ArrayList<Location>()
        val adapter = ArrayAdapter(
            this@HomeActivity,
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
        val myRef = databaseReference.child("Time")
        val list = ArrayList<Time>()
        val adapter = ArrayAdapter(
            this@HomeActivity,
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
            this@HomeActivity,
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