package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.WXPayBean

interface AuctionDepositContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setWXPay(bean: WXPayBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestDeposit(id: String)

    }

}