package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class ChangePasswordActivity : BaseActivity() {
    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "设置密码"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_change_password

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun start() {

    }

}