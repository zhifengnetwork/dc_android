package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AchievementDetailBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AchievementModel {
    fun getAchievement(): Observable<BaseBean<AchievementDetailBean>> {
        return RetrofitManager.service.getAchievement()
            .compose(SchedulerUtils.ioToMain())
    }
}