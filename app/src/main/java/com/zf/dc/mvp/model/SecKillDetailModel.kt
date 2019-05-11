package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.SecKillDetailBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillDetailModel {
    fun getSecKillDetail(id: String): Observable<BaseBean<SecKillDetailBean>> {
        return RetrofitManager.service.getSecKillDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}