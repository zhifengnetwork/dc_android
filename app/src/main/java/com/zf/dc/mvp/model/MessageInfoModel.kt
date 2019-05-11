package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MessageInfoBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MessageInfoModel {
    fun getMessageInfo(rec_id: String): Observable<BaseBean<MessageInfoBean>> {
        return RetrofitManager.service.getMessageInfo(rec_id)
            .compose(SchedulerUtils.ioToMain())
    }
}