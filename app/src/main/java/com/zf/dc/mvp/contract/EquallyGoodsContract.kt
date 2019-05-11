package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.CommendList

interface EquallyGoodsContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getEquallyGoods(bean: List<CommendList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<CommendList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestEquallyGoods(id: String, page: Int?, num: Int)
    }
}