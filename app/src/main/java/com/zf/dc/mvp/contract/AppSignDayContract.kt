package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AppSignBean
import com.zf.dc.mvp.bean.AppSignDayBean

interface AppSignDayContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //签到
        fun appSignSuccess(bean: AppSignBean)
        //签到列表
        fun getAppSignDay(bean: AppSignDayBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestAppSign()

        fun requestAppSignDay()
    }
}