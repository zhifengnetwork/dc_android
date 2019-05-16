package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.base.BaseFragmentAdapter
import com.zf.dc.ui.fragment.goodsdetail.EvaluationFragment
import com.zf.dc.ui.fragment.goodsdetail.GoodsDetailFragment
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetail2Activity : BaseActivity() {
    /**要传一个商品ID过来*/
    companion object {
        fun actionStart(context: Context?, goods_id: String, actionId: String? = "") {
            val intent = Intent(context, GoodsDetail2Activity::class.java)
            intent.putExtra("id", goods_id)
            intent.putExtra("actionId", actionId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        backLayout.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_goods_detail

    private var id = ""

    override fun initData() {
        id = intent.getStringExtra("id")
    }

    override fun initView() {
        val titles = arrayListOf("商品", "评论")
        val fmgs = listOf(GoodsDetailFragment.newInstance(id), EvaluationFragment.newInstance(id))
        val adapter = BaseFragmentAdapter(supportFragmentManager, fmgs, titles)
        goods_vp.adapter = adapter
        goods_tab.setupWithViewPager(goods_vp)
    }

    override fun initEvent() {

    }

    override fun start() {

    }

}