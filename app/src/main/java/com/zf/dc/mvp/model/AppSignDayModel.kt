package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AppSignBean
import com.zf.dc.mvp.bean.AppSignDayBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AppSignDayModel {
    fun requestAppSign(): Observable<BaseBean<AppSignBean>> {
        return RetrofitManager.service.requestAppSign().compose(SchedulerUtils.ioToMain())
    }
    fun getAppSignDay(): Observable<BaseBean<AppSignDayBean>> {
        return RetrofitManager.service.getSignDay().compose(SchedulerUtils.ioToMain())
    }
}