package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.DistributeOrderList

interface DistributeOrderContract {

    interface View : IBaseView {


        fun setDistributeOrder(bean: List<DistributeOrderList>)

        fun setLoadMore(bean: List<DistributeOrderList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestDistributeOrder(page: Int?)

    }

}