package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AttriBute

interface GoodsAttrContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        //商品属性
        fun getGoodsAttr(bean:List<AttriBute>)
    }
    interface Presenter:IPresenter<View>{
        fun requestGoodsAttr(goods_id:String)
    }
}