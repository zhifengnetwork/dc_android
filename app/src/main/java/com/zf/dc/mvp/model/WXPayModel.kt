package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.WXPayBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class WXPayModel {


    fun requestWXPay(order_sn: String): Observable<BaseBean<WXPayBean>> {
        return RetrofitManager.service.requestWXPay(order_sn)
            .compose(SchedulerUtils.ioToMain())
    }
}