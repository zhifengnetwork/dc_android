package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.ClassifyBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ClassifyModel{
    fun requestClassify(): Observable<BaseBean<List<ClassifyBean>>>{
        return RetrofitManager.service.getClassifyList()
            .compose(SchedulerUtils.ioToMain())
    }
}