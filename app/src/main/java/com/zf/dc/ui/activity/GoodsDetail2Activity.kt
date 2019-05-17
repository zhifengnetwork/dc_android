package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.base.BaseFragmentAdapter
import com.zf.dc.mvp.bean.TabEntity
import com.zf.dc.ui.fragment.goodsdetail.EvaluationFragment
import com.zf.dc.ui.fragment.goodsdetail.GoodsDetailFragment
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetail2Activity : BaseActivity() {
    /**要传一个商品ID过来*/
    companion object {
        private var mIndex = 0
        fun actionStart(context: Context?, goods_id: String, actionId: String? = "", index: Int? = mIndex) {
            val intent = Intent(context, GoodsDetail2Activity::class.java)
            intent.putExtra("id", goods_id)
            intent.putExtra("actionId", actionId)
            intent.putExtra("index", index)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        backLayout.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_goods_detail

    private var id = ""

    private var mActionId = ""

    override fun initData() {
        id = intent.getStringExtra("id")
        mActionId = intent.getStringExtra("actionId")
        mIndex = intent?.getIntExtra("index", mIndex) ?: mIndex
    }

    override fun initView() {
        val fmgs =
            arrayListOf(
                GoodsDetailFragment.newInstance(id, mActionId) as Fragment,
                EvaluationFragment.newInstance(id) as Fragment
            )
        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("商品", 0, 0))
        entitys.add(TabEntity("评论", 0, 0))
        goods_tab.setTabData(entitys, this, R.id.goods_fl, fmgs)
        goods_tab.currentTab = mIndex
    }

    override fun initEvent() {

    }

    override fun onResume() {
        super.onResume()
        mIndex = 0
    }

    override fun start() {

    }

}