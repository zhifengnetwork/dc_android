package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.ui.adapter.BenefitAdapter
import com.zf.dc.utils.StatusBarUtils
import com.zf.dc.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_benefit.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 公益基金账户
 */
class BenefitActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, BenefitActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        titleName.text = "公益基金账户"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    private val adapter by lazy { BenefitAdapter(this) }

    private val rvDivider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            1,
            ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun layoutId(): Int = R.layout.activity_benefit

    override fun initData() {
    }



    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(rvDivider)
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}