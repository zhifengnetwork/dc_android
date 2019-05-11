package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.mvp.bean.MessageList
import com.zf.dc.ui.activity.MessageInfoActivity
import com.zf.dc.utils.TimeUtils
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter(val context: Context?, val data: List<MessageList>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //标题
            message_title.text = data[position].message_title
            //时间戳转时间
            message_time.text = TimeUtils.myOrderTime(data[position].send_time)
        }
        holder.itemView.setOnClickListener {
            MessageInfoActivity.actionStart(context, data[position].rec_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}