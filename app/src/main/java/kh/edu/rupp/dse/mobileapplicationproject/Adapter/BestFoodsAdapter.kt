package kh.edu.rupp.dse.mobileapplicationproject.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kh.edu.rupp.dse.mobileapplicationproject.Activity.DetailActivity
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Foods
import kh.edu.rupp.dse.mobileapplicationproject.R

class BestFoodsAdapter(private val items: ArrayList<Foods>) : RecyclerView.Adapter<BestFoodsAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_best_deal, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTxt.text = if (item.Title.length > 16) {
            item.Title.substring(0, 15) + "..."
        } else {
            item.Title
        }
        holder.priceTxt.text = "$${item.Price}"
        holder.timeTxt.text = "${item.TimeValue} min"
        holder.starTxt.text = item.Star.toString()

        Glide.with(context)
            .load(item.ImagePath)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        val timeTxt: TextView = itemView.findViewById(R.id.timeTxt)
        val starTxt: TextView = itemView.findViewById(R.id.starTxt)
        val pic: ImageView = itemView.findViewById(R.id.pic)
    }
}
