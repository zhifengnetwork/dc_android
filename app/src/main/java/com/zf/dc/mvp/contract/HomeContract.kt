package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.HomeBean

interface HomeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setHome(bean: HomeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestHome()

    }

}