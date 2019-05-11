package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.OrderListBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderListModel {
    fun requestOrderList(type: String, page: Int,keyWord:String): Observable<BaseBean<List<OrderListBean>>> {
        return RetrofitManager.service.getOrderList(type, page, UriConstant.PER_PAGE,keyWord)
            .compose(SchedulerUtils.ioToMain())
    }
}