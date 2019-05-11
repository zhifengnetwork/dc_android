package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.DiscountBean

interface DiscountContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setDiscount(bean: List<DiscountBean>)

        fun setEmpty()
    }

    interface Presenter : IPresenter<View> {
        fun requestDiscount(status: String)

    }

}