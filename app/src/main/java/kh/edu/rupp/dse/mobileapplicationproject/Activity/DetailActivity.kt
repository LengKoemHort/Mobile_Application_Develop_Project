package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Foods
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityDetailBinding
import kh.edu.rupp.dse.mobileapplicationproject.Helperimport.ManagementCart

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var foodObject: Foods
    private var num = 1
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtra()
        setVariable()
    }

    private fun setVariable() {
        managementCart = ManagementCart(this)

        binding.backBtn.setOnClickListener { finish() }

        Glide.with(this)
            .load(foodObject.ImagePath)
            .into(binding.pic)

        binding.titleTxt.text = foodObject.Title
        binding.priceTxt.text = "$${foodObject.Price}"
        binding.detailTxt.text = foodObject.Description
        binding.rateTxt.text = foodObject.Star.toString()
        binding.timeTxt.text = "${foodObject.TimeValue} mins"
        binding.ratingBar.rating = foodObject.Star.toFloat()
        binding.totalTxt.text = "$${foodObject.Price}"

        binding.plusBtn.setOnClickListener {
            num++
            binding.numTxt.text = num.toString()
            binding.totalTxt.text = "$${foodObject.Price * num}"
        }

        binding.minusBtn.setOnClickListener {
            if (num > 1) {
                num--
                binding.numTxt.text = num.toString()
                binding.totalTxt.text = "$${foodObject.Price * num}"
            }
        }

        binding.addBtn.setOnClickListener {
            foodObject.NumberInCart = num
            managementCart.insertFood(foodObject)
        }
    }

    private fun getIntentExtra() {
        foodObject = intent.getSerializableExtra("object") as Foods
    }
}
