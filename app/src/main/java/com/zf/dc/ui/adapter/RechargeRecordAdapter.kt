package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.mvp.bean.RechargeRecordList
import com.zf.dc.utils.TimeUtils
import kotlinx.android.synthetic.main.item_recharge_record.view.*

class RechargeRecordAdapter(val context: Context, val data: List<RechargeRecordList>) :
    RecyclerView.Adapter<RechargeRecordAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recharge_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //支付方式
            pay_name.text = data[position].pay_name
            //时间
            ctime.text = TimeUtils.auctionTime(data[position].ctime)
            //金额
            account.text = data[position].account
            //状态
            pay_status.text = data[position].pay_status
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}