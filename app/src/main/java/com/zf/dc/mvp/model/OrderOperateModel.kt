package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderOperateModel {

    fun requestConfirmReceipt(orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestConfirmReceipt(orderId)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestCancelOrder(orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCancelOrder(orderId)
                .compose(SchedulerUtils.ioToMain())
    }
}