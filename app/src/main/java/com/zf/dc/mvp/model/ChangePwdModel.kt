package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class ChangePwdModel {
    fun changePwd(passold: String, password: String, password2: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.getUpdatePwd(passold, password, password2)
            .compose(SchedulerUtils.ioToMain())
    }
}