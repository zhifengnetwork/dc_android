package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AuctionList

interface AuctionListContract {

    interface View : IBaseView {


        fun setAuctionList(bean: List<AuctionList>)

        fun setLoadMore(bean: List<AuctionList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestAuctionList(page: Int?)

    }

}