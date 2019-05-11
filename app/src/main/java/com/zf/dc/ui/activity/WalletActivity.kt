package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.mvp.bean.MyWalletBean
import com.zf.dc.mvp.contract.MyWalletContract
import com.zf.dc.mvp.presenter.MyWalletPresenter
import com.zf.dc.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的钱包
 */
class WalletActivity : BaseActivity(), MyWalletContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getMyWallet(bean: MyWalletBean) {
        mData = bean
        setLayout()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, WalletActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )


        titleName.text = "我的钱包"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    //网络接收数据
    private var mData: MyWalletBean? = null

    private val presenter by lazy { MyWalletPresenter() }

    override fun layoutId(): Int = R.layout.activity_wallet

    override fun initData() {
    }


    override fun initView() {
        presenter.attachView(this)

    }

    override fun initEvent() {
        //账单明细
        accountState.setOnClickListener {
            AccountDetailsActivity.actionStart(this)
        }
        //提现明细
        cash_out.setOnClickListener {
            CashOutRecordActivity.actionStart(this)
        }

        //支付宝绑定
        aliPay.setOnClickListener {
            BindZfbActivity.actionStart(this, mData?.alipay, mData?.realname)
        }
    }

    override fun start() {
        presenter.requestMyWallet()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.requestMyWallet()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    //界面赋值
    private fun setLayout() {
        //账户余额
        user_money.text = mData?.user_money
        //用户积分
        pay_points.text = mData?.pay_points
        //优惠卷数
        coupon_num.text = mData?.coupon_num
        //是否绑定支付宝
        if (mData?.alipay != null) {
            zfb_label.text = "已添加"
            zfb_label.setTextColor(Color.rgb(38, 108, 232))
        } else {
            zfb_label.text = "未添加"
            zfb_label.setTextColor(Color.rgb(250, 20, 20))
        }

    }
}