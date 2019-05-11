package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MaterialList


interface MaterialListContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialList(bean: List<MaterialList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<MaterialList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialList(cid: String, page: Int?)
    }
}