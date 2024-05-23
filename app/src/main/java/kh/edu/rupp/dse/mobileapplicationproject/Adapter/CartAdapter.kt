package kh.edu.rupp.dse.mobileapplicationproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kh.edu.rupp.dse.mobileapplicationproject.Domain.Foods
import kh.edu.rupp.dse.mobileapplicationproject.Helper.ChangeNumberItemsListener
import kh.edu.rupp.dse.mobileapplicationproject.Helperimport.ManagementCart
import kh.edu.rupp.dse.mobileapplicationproject.R

class CartAdapter(
    private val list: ArrayList<Foods>,
    private val context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managementCart: ManagementCart = ManagementCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].Title
        holder.feeEachItem.text = "$${list[position].Price}"
        holder.totalEachItem.text = "${list[position].NumberInCart} x $${list[position].NumberInCart * list[position].Price}"
        holder.num.text = list[position].NumberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(list[position].ImagePath)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.pic)

        holder.plusItem.setOnClickListener {
            managementCart.plusNumberItem(list, position) {
                notifyDataSetChanged()
                changeNumberItemsListener.change()
            }
        }

        holder.minusItem.setOnClickListener {
            managementCart.minusNumberItem(list, position) {
                notifyDataSetChanged()
                changeNumberItemsListener.change()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTxt)
        val totalEachItem: TextView = itemView.findViewById(R.id.totaleachitemsTxt)
        val feeEachItem: TextView = itemView.findViewById(R.id.feeeachitemsTxt)
        val num: TextView = itemView.findViewById(R.id.numTxt)
        val minusItem: TextView = itemView.findViewById(R.id.minusBtn)
        val plusItem: TextView = itemView.findViewById(R.id.plusBtn)
        val pic: ImageView = itemView.findViewById(R.id.pic)
    }
}