package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AuctionBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionListModel {
    fun getAuctionList(page: Int): Observable<BaseBean<AuctionBean>> {
        return RetrofitManager.service.getAuctionList(page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}