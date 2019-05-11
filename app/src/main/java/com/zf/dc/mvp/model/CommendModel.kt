package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AppSignBean
import com.zf.dc.mvp.bean.CommendBean
import com.zf.dc.mvp.bean.MeBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class CommendModel {
    fun requestCommend(type: String, page: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.requestCommend(type, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestAppSign(): Observable<BaseBean<AppSignBean>> {
        return RetrofitManager.service.requestAppSign().compose(SchedulerUtils.ioToMain())
    }

    fun requestMe(): Observable<BaseBean<MeBean>> {
        return RetrofitManager.service.requestMe()
                .compose(SchedulerUtils.ioToMain())
    }
}