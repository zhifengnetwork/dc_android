package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.DiscountBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class DiscountModel {

    fun requestDisount(status: String): Observable<BaseBean<List<DiscountBean>>> {
        return RetrofitManager.service.getDiscount(status)
            .compose(SchedulerUtils.ioToMain())
    }
}