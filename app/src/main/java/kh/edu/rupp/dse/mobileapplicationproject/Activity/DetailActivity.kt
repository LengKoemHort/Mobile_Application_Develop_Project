package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kh.edu.rupp.dse.mobileapplicationproject.Helper.ManagementCart
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityDetailBinding
import kh.edu.rupp.dse.mobileapplicationproject.domain.Foods

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var food: Foods
    private var num = 1
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managementCart = ManagementCart(this)

        getBundleExtras()
        setVariable()
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
        Glide.with(this)
            .load(food.ImagePath)
            .into(binding.pic)

        binding.priceTxt.text = "$" + food.Price
        binding.titleTxt.text = food.Title
        binding.descriptionTxt.text = food.Description
        binding.ratingTxt.text = food.Star.toString() + " Rating"
        binding.ratingBar.rating = food.Star.toFloat()
        binding.totalTxt.text = num.toString()

        binding.plusBtn.setOnClickListener {
            num++
            binding.numTxt.text = num.toString()
            binding.totalTxt.text = (num * food.Price).toString()
        }

        binding.minusBtn.setOnClickListener {
            if (num > 1) {
                num--
                binding.numTxt.text = num.toString()
                binding.totalTxt.text = (num * food.Price).toString()
            }
        }

        binding.addBtn.setOnClickListener {
            food.NumberInCart = num
            managementCart.insertFood(food)
        }
    }

    private fun getBundleExtras() {
        val extras = intent.getSerializableExtra("food")
        if (extras != null && extras is Foods) {
            food = extras // Initialize the food property with the extras
        } else {
            // Handle the case where the extras are null or not of type Foods
        }
    }


}
