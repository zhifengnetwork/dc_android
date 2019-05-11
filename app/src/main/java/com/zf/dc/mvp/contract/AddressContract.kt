package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AddressBean

interface AddressContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun getAddress(bean: List<AddressBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestAddress()

    }

}