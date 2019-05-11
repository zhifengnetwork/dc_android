package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.LoginBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class LoginModel {
    fun login(phone: String, pwd: String): Observable<BaseBean<LoginBean>> {
        return RetrofitManager.service.login(phone, pwd)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestWeChatLogin(code: String): Observable<BaseBean<LoginBean>> {
        return RetrofitManager.service.requestWeChatLogin(code)
            .compose(SchedulerUtils.ioToMain())
    }
}