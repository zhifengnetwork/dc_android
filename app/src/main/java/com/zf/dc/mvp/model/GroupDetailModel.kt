package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.GroupDetailBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupDetailModel {
    fun getGroupDetail(id: String): Observable<BaseBean<GroupDetailBean>> {
        return RetrofitManager.service.getGroupDetail(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun getGroupMember(teamId: String): Observable<BaseBean<GroupDetailBean>> {
        return RetrofitManager.service.getGroupMember(teamId)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestAddCollect(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setCollectGoods(goods_id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestDelCollect(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delCollectGoods(goods_id)
                .compose(SchedulerUtils.ioToMain())
    }

}