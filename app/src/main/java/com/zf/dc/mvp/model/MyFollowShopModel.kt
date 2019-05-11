package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.MyFollowShopBean
import com.zf.dc.mvp.bean.ShopListBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFollowShopModel {
    fun getShopList(page: Int, num: Int, goodsnum: Int): Observable<BaseBean<ShopListBean>> {
        return RetrofitManager.service.getShopList(page, num, goodsnum).compose(SchedulerUtils.ioToMain())
    }

    fun getMyFollowShop(page: Int, num: Int): Observable<BaseBean<MyFollowShopBean>> {
        return RetrofitManager.service.getMyFollowShop(page, num).compose(SchedulerUtils.ioToMain())
    }

    fun delMyFollowShop(seller_id: String, type: String, collect_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delMyFollowShop(seller_id, type, collect_id).compose(SchedulerUtils.ioToMain())
    }
}