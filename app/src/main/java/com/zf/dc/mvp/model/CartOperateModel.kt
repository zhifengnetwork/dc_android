package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.CartSelectBean
import com.zf.dc.mvp.bean.GoodsSpecBean
import com.zf.dc.mvp.bean.SpecBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.RequestBody

class CartOperateModel {

    fun setCount(id: String, num: Int): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.cartCount(id, num)
                .compose(SchedulerUtils.ioToMain())
    }

    fun setCartSelect(cart: RequestBody): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestCartSelect(cart)
                .compose(SchedulerUtils.ioToMain())
    }

    fun setCheckAll(type: Int): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestCartCheckAll(type)
                .compose(SchedulerUtils.ioToMain())
    }

    fun setDeleteCart(id: RequestBody): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestDeleteCart(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestSpec(id: String): Observable<BaseBean<List<List<SpecBean>>>> {
        return RetrofitManager.service.requestGoodsSpec(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestChangeSpec(cartId: String, itemId: String): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestChangeSpec(cartId, itemId)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestSpecInfo(key: String, goodsId: String): Observable<BaseBean<GoodsSpecBean>> {
        return RetrofitManager.service.requestSpecInfo(key, goodsId)
                .compose(SchedulerUtils.ioToMain())
    }
}