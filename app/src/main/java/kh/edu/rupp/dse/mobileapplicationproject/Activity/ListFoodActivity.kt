package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kh.edu.rupp.dse.mobileapplicationproject.Adapter.ListFoodAdapter
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Foods
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityListFoodBinding

class ListFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFoodBinding
    private lateinit var adapterListFood: RecyclerView.Adapter<*>
    private var categoryId: Int = 0
    private lateinit var categoryName: String
    private lateinit var searchText: String
    private var isSearch: Boolean = false
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListFoodBinding.inflate(layoutInflater)
        databaseReference = FirebaseDatabase.getInstance().reference
        setContentView(binding.root)

        getIntentExtra()
        initList()
        setVariable()
    }

    private fun setVariable() {

    }

    private fun initList() {
        val myRef = databaseReference.child("Foods")
        binding.progressBarFoodList.visibility = View.VISIBLE
        val list = ArrayList<Foods>()

        val query: Query = if (isSearch) {
            myRef.orderByChild("Title").startAt(searchText).endAt(searchText + "\uf8ff")
        } else {
            myRef.orderByChild("CategoryId").equalTo(categoryId.toDouble())
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Foods::class.java)!!)
                    }
                    if (list.size > 0) {
                        binding.foodListView.layoutManager = GridLayoutManager(this@ListFoodActivity, 2)
                        adapterListFood = ListFoodAdapter(list)
                        binding.foodListView.adapter = adapterListFood
                    }
                    binding.progressBarFoodList.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBarFoodList.visibility = View.GONE
            }
        })
    }

    private fun getIntentExtra() {
        categoryId = intent.getIntExtra("CategoryId", 0)
        categoryName = intent.getStringExtra("CategoryName") ?: ""
        searchText = intent.getStringExtra("text") ?: ""
        isSearch = intent.getBooleanExtra("isSearch", false)

        binding.titleTxt.text = categoryName
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}