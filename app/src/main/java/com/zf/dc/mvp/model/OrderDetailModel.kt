package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.OrderListBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderDetailModel {
    fun getOrderDetail(id: String): Observable<BaseBean<OrderListBean>> {
        return RetrofitManager.service.getOrderDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}