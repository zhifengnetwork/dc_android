package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.CommendBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class RecommendGoodsModel {
    fun getRecommendGoods(id: String, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getRecommendGoods(id, num)
            .compose(SchedulerUtils.ioToMain())
    }
}