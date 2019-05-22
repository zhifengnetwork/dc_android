package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter

interface ChangePwdContract {
    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun changePwdSuccess(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestChangePwd(passold: String, password: String, password2: String)
    }
}