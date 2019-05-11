package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.GoodEvaBean

interface GoodEvaContract {

    interface View : IBaseView {


        fun setGoodEva(bean: GoodEvaBean)

        fun setLoadMore(bean: GoodEvaBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestGoodEva(goodId: String, type: Int, page: Int?)

    }

}