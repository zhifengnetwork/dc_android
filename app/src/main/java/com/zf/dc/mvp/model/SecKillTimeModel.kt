package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.SecKillTimeBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillTimeModel {

    fun getSecKillTime(): Observable<BaseBean<SecKillTimeBean>> {
        return RetrofitManager.service.getSecKillTimeList()
                .compose(SchedulerUtils.ioToMain())
    }
}