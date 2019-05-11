package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.CashOutBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class CashOutRecordModel {
    fun getCashOutRecordList(page: Int, num: Int): Observable<BaseBean<CashOutBean>> {
        return RetrofitManager.service.getCashOutList(page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}