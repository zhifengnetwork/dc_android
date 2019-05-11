package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MyMemberBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyMemberModel {
    fun getMyMember(page: Int, num: Int, next_user_id: String): Observable<BaseBean<List<MyMemberBean>>> {
        return RetrofitManager.service.getMyMember(page, num, next_user_id).compose(SchedulerUtils.ioToMain())
    }
}