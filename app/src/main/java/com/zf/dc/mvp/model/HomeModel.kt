package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.HomeBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class HomeModel {
    fun requestHome(): Observable<BaseBean<HomeBean>> {
        return RetrofitManager.service.requestHome()
                .compose(SchedulerUtils.ioToMain())
    }
}