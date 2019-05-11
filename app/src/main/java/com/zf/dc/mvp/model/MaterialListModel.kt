package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MaterialListBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialListModel {
    fun getMaterialList(cid: String, page: Int, num: Int): Observable<BaseBean<MaterialListBean>> {
        return RetrofitManager.service.getMaterialList(cid, page, num).compose(SchedulerUtils.ioToMain())
    }
}