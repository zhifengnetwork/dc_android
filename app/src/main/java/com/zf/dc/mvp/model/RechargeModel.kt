package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.RechargeRecordBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class RechargeModel {
    fun getRechargeRecordList(page: Int, num: Int): Observable<BaseBean<RechargeRecordBean>> {
        return RetrofitManager.service.getRechargeList(page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}