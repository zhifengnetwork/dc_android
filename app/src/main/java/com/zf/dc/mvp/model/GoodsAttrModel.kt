package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.GoodsAttrBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodsAttrModel{
    fun getGoodsAttr(goods_id:String):Observable<BaseBean<GoodsAttrBean>>{
        return RetrofitManager.service.getGoodsAttr(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }
}