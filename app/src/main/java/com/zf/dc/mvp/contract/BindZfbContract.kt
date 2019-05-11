package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter

interface BindZfbContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun bindZfbSuccess(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestBindZfb(zfb_account: String, realname: String)
    }
}