package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MaterialClassifyList

interface MaterialClassifyContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialClassify(bean: List<MaterialClassifyList>)

    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialClassify()

    }
}