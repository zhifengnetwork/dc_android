package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.DistributeBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class DistributeModel {

    fun requestDistribute(): Observable<BaseBean<DistributeBean>> {
        return RetrofitManager.service.requestDistribute()
                .compose(SchedulerUtils.ioToMain())
    }
}