package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MyMemberOrderBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MemberOrderModel {
    fun getMemberOrder(page: Int, num: Int, next_user_id: String): Observable<BaseBean<MyMemberOrderBean>> {
        return RetrofitManager.service.getMyMemberOrder(page, num, next_user_id).compose(SchedulerUtils.ioToMain())
    }
}