package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AchievementList

interface AchievementContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getAchievement(bean: List<AchievementList>)
    }
    interface Presenter:IPresenter<View>{
        fun requestAchievementDetail()
    }
}