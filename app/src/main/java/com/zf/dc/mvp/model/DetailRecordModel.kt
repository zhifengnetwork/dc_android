package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.DetailRecordBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class DetailRecordModel {
    fun requestDetailRecord(page: Int): Observable<BaseBean<DetailRecordBean>> {
        return RetrofitManager.service.getDetailRecord(page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}