package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MaterialDetailBean

interface MaterialDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialDetail(bean: MaterialDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialDetail(id: String)
    }
}