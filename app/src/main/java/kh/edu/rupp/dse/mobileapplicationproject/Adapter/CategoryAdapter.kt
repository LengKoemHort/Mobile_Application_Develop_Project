package kh.edu.rupp.dse.mobileapplicationproject.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.dse.mobileapplicationproject.Activity.ListFoodActivity
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Category
import kh.edu.rupp.dse.mobileapplicationproject.R

class CategoryAdapter(private val items: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTxt.text = item.Name

        // Set the background image based on the position
        val backgroundResourceId = getBackgroundResourceId(position)
        holder.pic.setBackgroundResource(backgroundResourceId)

        // Load the image using Glide
        val drawableResourceId = context.resources.getIdentifier(item.ImagePath, "drawable", context.packageName)
        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListFoodActivity::class.java)
            intent.putExtra("CategoryId", items[position].Id)
            intent.putExtra("CategoryName", items[position].Name)
            context.startActivity(intent)
        }
    }

    private fun getBackgroundResourceId(position: Int): Int {
        return when (position % 8) {
            0 -> R.drawable.cat_0_background
            1 -> R.drawable.cat_1_background
            2 -> R.drawable.cat_2_background
            3 -> R.drawable.cat_3_background
            4 -> R.drawable.cat_4_background
            5 -> R.drawable.cat_5_background
            6 -> R.drawable.cat_6_background
            7 -> R.drawable.cat_7_background
            else -> R.drawable.cat_2_background
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.catNameTxt)
        val pic: ImageView = itemView.findViewById(R.id.imgCat)
    }

}