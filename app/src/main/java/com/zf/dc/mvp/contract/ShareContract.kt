package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.ShareBean

interface ShareContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setShare(bean: ShareBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestShare()

    }

}