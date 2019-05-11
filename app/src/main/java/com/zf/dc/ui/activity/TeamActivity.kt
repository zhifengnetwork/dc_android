package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.mvp.bean.AchievementList
import com.zf.dc.mvp.contract.AchievementContract
import com.zf.dc.mvp.presenter.AchievementPresenter
import com.zf.dc.showToast
import com.zf.dc.ui.adapter.TeamDetailAdapter
import com.zf.dc.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的团队
 */
class TeamActivity : BaseActivity(), AchievementContract.View {
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun getAchievement(bean: List<AchievementList>) {
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, TeamActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)

        back.setOnClickListener {
            finish()
        }
        titleName.text = "我的团队业绩"
        rightLayout.visibility = View.INVISIBLE

    }

    private val presenter by lazy { AchievementPresenter() }

    private var mData = ArrayList<AchievementList>()

    private val adapter by lazy { TeamDetailAdapter(this, mData) }

    override fun layoutId(): Int = R.layout.activity_team

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun initEvent() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestAchievementDetail()
    }

}