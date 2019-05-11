package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AuctionDetailBean
import com.zf.dc.mvp.bean.AuctionPriceBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionDetailModel {
    fun getAuctionDetail(id: String): Observable<BaseBean<AuctionDetailBean>> {
        return RetrofitManager.service.getAuctionDetail(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun getAuctionPrice(id: String): Observable<BaseBean<AuctionPriceBean>> {
        return RetrofitManager.service.getAuctionPrice(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestBid(id: String, price: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestBid(id, price)
                .compose(SchedulerUtils.ioToMain())
    }

}