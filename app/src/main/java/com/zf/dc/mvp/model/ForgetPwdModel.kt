package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ForgetPwdModel {

    fun requestContract(mobile: String, code: String, scene: Int): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestContrast(mobile, code, scene)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestChangePwd(mobile: String, password: String, password2: String, scene: Int): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestChangePwd(mobile, password, password2, scene)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestCode(scene: Int, mobile: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCode(scene, mobile)
            .compose(SchedulerUtils.ioToMain())
    }

}