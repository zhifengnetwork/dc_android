package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.CommendBean
import com.zf.dc.mvp.bean.MyFollowBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFollowModel {
    fun getMyFollow(page: Int, num: Int): Observable<BaseBean<MyFollowBean>> {
        return RetrofitManager.service.getMyFollow(page, num)
            .compose(SchedulerUtils.ioToMain())
    }

    fun delCollectGoods(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delCollectGoods(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getLoveGoods(type: String, page: Int, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getLoveGoods(type, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}