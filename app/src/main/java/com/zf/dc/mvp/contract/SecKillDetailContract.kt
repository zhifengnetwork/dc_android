package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.SecKillDetailBean

interface SecKillDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setSecKillDetail(bean: SecKillDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillDetail(id: String)

    }

}