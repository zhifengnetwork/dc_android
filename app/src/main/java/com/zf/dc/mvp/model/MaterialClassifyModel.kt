package com.zf.dc.mvp.model


import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MaterialClassifyBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialClassifyModel {

    fun getMaterialClassify(): Observable<BaseBean<MaterialClassifyBean>> {
        return RetrofitManager.service.getMaterialClassify().compose(SchedulerUtils.ioToMain())
    }
}