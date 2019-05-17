package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.mvp.bean.DistributeBean
import com.zf.dc.mvp.contract.DistributeContract
import com.zf.dc.mvp.presenter.DistributePresenter
import com.zf.dc.showToast
import com.zf.dc.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_distribute.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的分销
 */
class DistributeActivity : BaseActivity(), DistributeContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setDistribute(bean: DistributeBean) {
        totalMoney.text = bean.money_total.money_total
        mostUserMoney.text = bean.money_total.max_moneys
        userSum.text = bean.money_total.moneys
        teamTotal.text = bean.underling_number
        superId.text = bean.leader.user_id
        superName.text = bean.leader.nickname
        myId.text = bean.user_id
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "我的分销"
        rightLayout.visibility = View.INVISIBLE
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, DistributeActivity::class.java))
        }
    }

    private val presenter by lazy { DistributePresenter() }

    override fun layoutId(): Int = R.layout.activity_distribute

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun initEvent() {

        teamDetail.setOnClickListener {
            TeamActivity.actionStart(this)
        }

        teamList.setOnClickListener {
            MyMemberActivity.actionStart(this)
        }

        distributeOrder.setOnClickListener {
            DistributeOrderActivity.actionStart(this)
        }

        detailRecord.setOnClickListener {
            DetailRecordActivity.actionStart(this)
        }

        refresh.setOnClickListener {
            start()
        }
    }

    override fun start() {
        presenter.requestDistribute()
    }
}