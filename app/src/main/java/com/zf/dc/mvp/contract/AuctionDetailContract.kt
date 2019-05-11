package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AuctionDetailBean
import com.zf.dc.mvp.bean.AuctionPriceBean

interface AuctionDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setAuctionDetail(bean: AuctionDetailBean)

        fun setAuctionPrice(bean: AuctionPriceBean)

        fun setBid()

    }

    interface Presenter : IPresenter<View> {

        //出价
        fun requestBid(id: String, price: String)

        fun requestAuctionDetail(id: String)

        fun requestAuctionPrice(id: String)

    }

}