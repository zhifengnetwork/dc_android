package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AdvertBean
import com.zf.dc.mvp.bean.SecKillListBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillListModel {
    fun getSecKillList(startTime: String, endTime: String, page: Int): Observable<BaseBean<SecKillListBean>> {
        return RetrofitManager.service.getSecKillList(startTime, endTime, page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}