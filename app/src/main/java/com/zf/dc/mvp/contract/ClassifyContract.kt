package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.ClassifyBean

interface ClassifyContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun setTitle(bean: List<ClassifyBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestClassify()
    }
}