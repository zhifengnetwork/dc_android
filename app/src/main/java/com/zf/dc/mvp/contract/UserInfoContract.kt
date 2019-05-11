package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.UserInfoBean

interface UserInfoContract {
    interface View : IBaseView {

        fun setUserInfo(bean:UserInfoBean)

        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestUserInfo()
    }
}