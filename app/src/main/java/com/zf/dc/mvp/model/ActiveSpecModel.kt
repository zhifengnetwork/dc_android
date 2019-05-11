package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.GoodsSpecBean
import com.zf.dc.mvp.bean.SpecBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ActiveSpecModel {

    fun requestSpec(id: String): Observable<BaseBean<List<List<SpecBean>>>> {
        return RetrofitManager.service.requestGoodsSpec(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestSpecInfo(key: String, goodsId: String): Observable<BaseBean<GoodsSpecBean>> {
        return RetrofitManager.service.requestSpecInfo(key, goodsId)
                .compose(SchedulerUtils.ioToMain())
    }
}