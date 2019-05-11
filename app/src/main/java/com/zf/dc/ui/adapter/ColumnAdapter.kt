package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.ui.activity.*

import kotlinx.android.synthetic.main.me_specail_item.view.*

class ColumnAdapter(val context: Context?) : RecyclerView.Adapter<ColumnAdapter.ViewHolder>() {

    private val imgList = arrayOf(
            R.drawable.activity2, R.drawable.ic_withdrawals, R.drawable.auction, R.drawable.ic_assemble,
            R.drawable.ic_team, R.drawable.live, R.drawable.game, R.drawable.ic_beauty,
            R.drawable.virtualcurrency, R.drawable.ic_pickupgoods, R.drawable.theme, R.drawable.yue
    )
    private val titleList =
            arrayOf(
                    "我的活动", "奖金提现", "拍卖", "拼团",
                    "我的团队", "直播", "小游戏", "美容专区",
                    "虚拟币", "线下取货", "切换主题", "账户明细"
            )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.me_specail_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.me_specail_iv.setImageResource(imgList[position])
        holder.itemView.me_specail_name.text = titleList[position]
        holder.itemView.setOnClickListener {
            when (position) {
                2 -> ActionActivity.actionStart(context, ActionActivity.AUCTION)
                3 -> ActionActivity.actionStart(context, ActionActivity.GROUP)
                1 -> BonusActivity.actionStart(context)
                9 -> PickupActivity.actionStart(context)
                4 -> TeamActivity.actionStart(context)
//                5 -> LiveActivity.actionStart(context)
                11 -> AccountDetailsActivity.actionStart(context)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}