package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MaterialDetailBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialDetailModel {
    fun getMaterialDetail(id: String): Observable<BaseBean<MaterialDetailBean>> {
        return RetrofitManager.service.getMaterialDetail(id).compose(SchedulerUtils.ioToMain())
    }
}