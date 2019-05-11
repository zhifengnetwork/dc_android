package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.ui.activity.AccountDetailsActivity
import com.zf.dc.ui.activity.ActionActivity
import com.zf.dc.ui.activity.BonusActivity
import com.zf.dc.ui.activity.TeamActivity
import kotlinx.android.synthetic.main.me_specail_item.view.*

class ColumnAdapter(val context: Context?) : RecyclerView.Adapter<ColumnAdapter.ViewHolder>() {

    private val imgList = arrayOf(
        R.drawable.ic_withdrawals,
        R.drawable.auction,
        R.drawable.ic_assemble,
        R.drawable.ic_team,
        R.drawable.yue
    )
    private val titleList =
        arrayOf(
            "奖金提现",
            "拍卖",
            "拼团",
            "我的团队",
            "账户明细"
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.me_specail_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = titleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.me_specail_iv.setImageResource(imgList[position])
        holder.itemView.me_specail_name.text = titleList[position]
        holder.itemView.setOnClickListener {
            when (position) {
                1 -> ActionActivity.actionStart(context, ActionActivity.AUCTION)
                2 -> ActionActivity.actionStart(context, ActionActivity.GROUP)
                0 -> BonusActivity.actionStart(context)
                3 -> TeamActivity.actionStart(context)
                4 -> AccountDetailsActivity.actionStart(context)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}