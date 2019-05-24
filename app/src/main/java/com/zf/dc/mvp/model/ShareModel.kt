package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.ShareBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShareModel {

    fun requestShare(): Observable<BaseBean<ShareBean>> {
        return RetrofitManager.service.requestShare()
            .compose(SchedulerUtils.ioToMain())
    }
}