package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.base.BaseFragmentAdapter
import com.zf.dc.ui.fragment.MessageFragment
import com.zf.dc.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 站内消息
 * */
class MessageActivity : BaseActivity() {


    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MessageActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        titleName.text = "站内信息"
        back.setOnClickListener { finish() }
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_message

    override fun initData() {

    }

    override fun initView() {
        val titles = arrayListOf("站内", "消息")
        val fmgs = listOf(MessageFragment.newInstance(MessageFragment.BUY), MessageFragment.newInstance(MessageFragment.SELL))
        val adapter = BaseFragmentAdapter(supportFragmentManager, fmgs, titles)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun initEvent() {

    }

    override fun start() {

    }


}
