package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class BindZfbModel {
    fun setBindZfb(zfb_account: String, realname: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setBindZfb(zfb_account, realname)
            .compose(SchedulerUtils.ioToMain())
    }
}