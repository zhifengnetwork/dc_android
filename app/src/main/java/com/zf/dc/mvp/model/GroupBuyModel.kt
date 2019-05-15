package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.GroupBuyBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupBuyModel {
    fun requestGroupBuy(type: String, page: Int): Observable<BaseBean<GroupBuyBean>> {
        return RetrofitManager.service.requestGroupBuyList(type, page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}