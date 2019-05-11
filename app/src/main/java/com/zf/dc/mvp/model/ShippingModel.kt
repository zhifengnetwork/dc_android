package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.ShippingBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShippingModel {
    fun requestShipping(orderId: String): Observable<BaseBean<ShippingBean>> {
        return RetrofitManager.service.requestShipping(orderId)
                .compose(SchedulerUtils.ioToMain())
    }
}