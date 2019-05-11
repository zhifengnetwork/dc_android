package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter

interface ForgetPwdContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setContract()

        fun setChangePwd()

        fun setCode(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestContract(mobile: String, code: String)

        fun requestChangePwd(mobile: String, password: String, password2: String, scene: Int)

        fun requestCode(scene: Int, mobile: String)
    }

}