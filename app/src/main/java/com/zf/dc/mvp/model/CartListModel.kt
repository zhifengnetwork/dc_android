package com.zf.dc.mvp.model

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.CartBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class CartListModel {
    fun getCartList(page: Int): Observable<BaseBean<CartBean>> {
        return RetrofitManager.service.getCartList(page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}