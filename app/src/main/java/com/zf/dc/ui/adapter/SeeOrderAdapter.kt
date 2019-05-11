package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.mvp.bean.MemberOrderList
import com.zf.dc.utils.TimeUtils
import kotlinx.android.synthetic.main.item_see_order.view.*

class SeeOrderAdapter(val context: Context, val data: List<MemberOrderList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_see_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            order_id.text = data[position].order_sn
            user_name.text = data[position].consignee
            order_time.text = TimeUtils.getYmd(data[position].add_time)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}