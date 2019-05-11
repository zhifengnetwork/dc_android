package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AdvertList
import com.zf.dc.mvp.bean.BonusBean

interface BonusContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getBonus(bean: BonusBean)
        //广告图
        fun getAdList(bean: List<AdvertList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestBonus()

        fun requestAdList(pid: String)
    }
}