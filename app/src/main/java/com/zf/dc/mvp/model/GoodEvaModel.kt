package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.GoodEvaBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodEvaModel {

    fun getGoodEva(goodId: String, type: Int, page: Int): Observable<BaseBean<GoodEvaBean>> {
        return RetrofitManager.service.getGoodEva(goodId, type, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}