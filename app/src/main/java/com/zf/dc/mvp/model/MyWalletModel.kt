package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MyWalletBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyWalletModel {
    fun getMyWallet(): Observable<BaseBean<MyWalletBean>> {
        return RetrofitManager.service.getMyWallet().compose(SchedulerUtils.ioToMain())
    }
}