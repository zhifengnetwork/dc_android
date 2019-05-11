package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AdvertBean
import com.zf.dc.mvp.bean.ClassifyProductBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ClassifyProductModel {
    fun requestClassifyProduct(cat_id: String): Observable<BaseBean<List<ClassifyProductBean>>> {
        return RetrofitManager.service.getClassifyProduct(cat_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}