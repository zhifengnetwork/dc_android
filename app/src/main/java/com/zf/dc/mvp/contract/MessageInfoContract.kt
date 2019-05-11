package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MessageInfo

interface MessageInfoContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMessageInfo(bean: MessageInfo)
    }

    interface Presenter : IPresenter<View> {
        fun requestMessageInfo(rec_id: String)
    }
}