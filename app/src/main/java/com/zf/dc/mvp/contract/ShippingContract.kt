package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.ShippingBean

interface ShippingContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setShipping(bean: ShippingBean)

        fun setEmpty()
    }

    interface Presenter : IPresenter<View> {
        fun requestShipping(orderId: String)

    }

}