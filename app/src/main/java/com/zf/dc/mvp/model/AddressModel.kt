package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AddressBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressModel {
    fun requestAddress(): Observable<BaseBean<List<AddressBean>>> {
        return RetrofitManager.service.getAddressList()
            .compose(SchedulerUtils.ioToMain())
    }
}