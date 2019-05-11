package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AdvertBean
import com.zf.dc.mvp.bean.BonusBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class BonusModel {
    fun getBonus(): Observable<BaseBean<BonusBean>> {
        return RetrofitManager.service.getBonus().compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}