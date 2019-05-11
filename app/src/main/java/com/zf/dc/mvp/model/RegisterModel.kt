package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class RegisterModel {

    fun requestRegister(nickname: String, username: String, password: String, password2: String, code: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.register(nickname, username, password, password2, code)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestCode(scene: Int, mobile: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCode(scene, mobile)
                .compose(SchedulerUtils.ioToMain())
    }

}