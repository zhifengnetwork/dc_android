package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter

interface HotSearchContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setHotSearch(bean: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestHotSearch()

    }

}