package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.OrderListBean

interface OrderListContract {

    interface View : IBaseView {

        fun loadMoreError(msg: String, errorCode: Int)

        fun showError(msg: String, errorCode: Int)

        fun setFinishRefresh(bean: List<OrderListBean>)

        fun setFinishLoadMore(bean: List<OrderListBean>)

        fun setEmptyOrder()

        fun setLoadComplete()

    }

    interface Presenter : IPresenter<View> {

        fun requestOrderList(type: String, page: Int?, keyWord: String)

    }

}