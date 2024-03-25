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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kh.edu.rupp.dse.mobileapplicationproject.R
import kh.edu.rupp.dse.mobileapplicationproject.domain.Category

class CategoryAdapter(private val items: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleText.text = item.Name

        val radius = 10f
        val decorView = (context as Activity).window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground: Drawable = decorView.background

        holder.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)
        holder.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        holder.blurView.clipToOutline = true

        val drawableResourceId = holder.itemView.resources.getIdentifier(item.ImagePath, "drawable", context.packageName)

        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleCatText)
        val pic: ImageView = itemView.findViewById(R.id.imgCat)
        val blurView: BlurView = itemView.findViewById(R.id.blurView)
    }
}
