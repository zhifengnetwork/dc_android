package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.DistributeBean

interface DistributeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setDistribute(bean: DistributeBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestDistribute()

    }

}