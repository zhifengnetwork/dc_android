package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.LoginBean

interface LoginContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loginSuccess(bean: LoginBean)

        fun setWeChatLogin(bean: LoginBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestLogin(phone: String, pwd: String)

        fun requestWeChatLogin(code: String)

    }

}