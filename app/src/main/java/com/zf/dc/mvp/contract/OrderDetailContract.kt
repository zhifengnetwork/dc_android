package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.OrderListBean

interface OrderDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setOrderDetail(bean: OrderListBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestOrderDetail(id: String)

    }

}