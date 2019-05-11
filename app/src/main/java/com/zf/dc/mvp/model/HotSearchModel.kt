package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class HotSearchModel {
    fun requestHotSearch(): Observable<BaseBean<String>> {
        return RetrofitManager.service.requestHotSearch()
                .compose(SchedulerUtils.ioToMain())
    }
}