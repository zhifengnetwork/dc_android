package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MyFootBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFootModel {
    fun getMyFoot(page: Int, num: Int): Observable<BaseBean<List<MyFootBean>>> {
        return RetrofitManager.service.getMyFoot(page, num).compose(SchedulerUtils.ioToMain())
    }

    fun setMyFoot(visit_ids: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setMyFoot(visit_ids).compose(SchedulerUtils.ioToMain())
    }

    fun clearMyFoot(): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.clearMyFoot().compose(SchedulerUtils.ioToMain())
    }
}