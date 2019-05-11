package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.SecKillTimeBean

interface SecKillTimeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setSecKillTime(bean: SecKillTimeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillTime()

    }

}