package com.zf.dc.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.dc.R
import com.zf.dc.mvp.bean.ClassifyProductBean
import com.zf.dc.ui.activity.GoodsDetailActivity
import com.zf.dc.utils.GlideUtils
import kotlinx.android.synthetic.main.item_classify_right_shop.view.*

class ClassifyRightAdapter(val context: Context?, val mData: ArrayList<ClassifyProductBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: ArrayList<ClassifyProductBean> = mData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_classify_right_shop, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            GlideUtils.loadUrlImage(context, data[position].original_img, goods_img)
            goods_name.text = data[position].goods_name
        }
        holder.itemView.setOnClickListener {
            GoodsDetailActivity.actionStart(context, data[position].goods_id)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}