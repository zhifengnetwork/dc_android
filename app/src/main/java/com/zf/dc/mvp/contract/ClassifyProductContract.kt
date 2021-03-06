package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AdvertList
import com.zf.dc.mvp.bean.ClassifyProductBean

interface ClassifyProductContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun setProduct(bean :List<ClassifyProductBean>)
        //广告图片
        fun getAdList(bean: List<AdvertList>)
    }
    interface Presenter:IPresenter<View>{
        fun requestClassifyProduct(cat_id:String)

        fun requestAdList(pid: String)
    }
}