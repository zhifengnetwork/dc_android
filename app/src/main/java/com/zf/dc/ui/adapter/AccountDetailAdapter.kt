package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.mvp.bean.AccountDetailList
import kotlinx.android.synthetic.main.item_account_detail.view.*

class AccountDetailAdapter(val context: Context?, val data: List<AccountDetailList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_account_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            //订单编号
            order_sn.text = data[position].order_sn
            //金额
            user_money.text = data[position].user_money
            //日期
            change_time.text = data[position].change_data
            //描述
            desc.text = data[position].desc

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}