package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter

interface OrderOperateContract {

    interface View : IBaseView {

        fun showOperateError(msg: String, errorCode: Int)

        fun setCancelOrder()

        fun setConfirmReceipt()
    }

    interface Presenter : IPresenter<View> {

        fun requestCancelOrder(orderId: String)

        fun requestConfirmReceipt(orderId: String)
    }

}