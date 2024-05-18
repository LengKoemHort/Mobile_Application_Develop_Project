package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityCartBinding
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.dse.mobileapplicationproject.Adapter.CartAdapter
import kh.edu.rupp.dse.mobileapplicationproject.Helper.ChangeNumberItemsListener
import kh.edu.rupp.dse.mobileapplicationproject.Helperimport.ManagementCart

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        setVariable()
        calculateCart()
        initList()
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
        binding.cartView.adapter = adapter  // Ensure the adapter is set here
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

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            println("Back button clicked")
            finish()
        }
    }
}