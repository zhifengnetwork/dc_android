package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.UserInfoBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class UserInfoModel {
    fun requestUserInfo(): Observable<BaseBean<UserInfoBean>> {
        return RetrofitManager.service.getUserInfo()
            .compose(SchedulerUtils.ioToMain())
    }
}