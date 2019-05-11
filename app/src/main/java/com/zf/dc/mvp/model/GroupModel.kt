package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.GroupBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupModel {
    fun getGroup(page: Int): Observable<BaseBean<List<GroupBean>>> {
        return RetrofitManager.service.getGroupList(page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}