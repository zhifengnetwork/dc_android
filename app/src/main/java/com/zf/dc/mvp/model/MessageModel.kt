package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MessageBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MessageModel {
    fun getMessage(page: Int, num: Int, type: String): Observable<BaseBean<MessageBean>> {
        return RetrofitManager.service.getMessage(page, num, type)
            .compose(SchedulerUtils.ioToMain())
    }
}