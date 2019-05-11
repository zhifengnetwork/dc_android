package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.CartBean

interface CartListContract {

    interface View : IBaseView {


        fun setRefreshCart(bean: CartBean)

        fun setLoadMoreCart(bean: CartBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestCartList(page: Int?)

    }

}