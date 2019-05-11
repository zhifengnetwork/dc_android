package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AccountDetailBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AccountDetailModel {
    fun getAccountDetail(type: String, page: Int, num: Int): Observable<BaseBean<AccountDetailBean>> {
        return RetrofitManager.service.getAccountInfo(type, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}