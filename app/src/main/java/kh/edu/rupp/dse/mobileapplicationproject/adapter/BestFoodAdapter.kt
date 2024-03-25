package kh.edu.rupp.dse.mobileapplicationproject.adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kh.edu.rupp.dse.mobileapplicationproject.domain.Foods
import kh.edu.rupp.dse.mobileapplicationproject.R

class BestFoodAdapter(private val items: ArrayList<Foods>) : RecyclerView.Adapter<BestFoodAdapter.ViewHolder>() {
    private lateinit var context: Context

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_best_food, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleText.text = item.Title
        holder.priceText.text = "$${item.Price}"
        holder.starText.text = item.Star.toString()
        holder.timeText.text =  "${item.TimeValue} min"

        val radius = 10f
        val decorView = (context as Activity).window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground: Drawable = decorView.background

        holder.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)
        holder.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        holder.blurView.clipToOutline = true

        Glide.with(context)
            .load(item.ImagePath)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.pic)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val priceText: TextView = itemView.findViewById(R.id.priceText)
        val starText: TextView = itemView.findViewById(R.id.starText)
        val timeText: TextView = itemView.findViewById(R.id.timeText)
        val pic: ImageView = itemView.findViewById(R.id.img)
        val blurView: BlurView = itemView.findViewById(R.id.blueView)
    }
}
