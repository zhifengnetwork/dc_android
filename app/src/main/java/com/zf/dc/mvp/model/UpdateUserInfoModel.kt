package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.ChangeUserBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class UpdateUserInfoModel {

    fun uploadHead(head: MultipartBody.Part): Observable<BaseBean<String>> {
        return RetrofitManager.service.uploadMemberIcon(head)
            .compose(SchedulerUtils.ioToMain())
    }

    fun changeUserInfo(
        nickname: String,
        mobile: String,
        sex: String,
        birthyear: String,
        birthmonth: String,
        birthday: String
    ): Observable<BaseBean<ChangeUserBean>> {
        return RetrofitManager.service.updateUserInfo(nickname, mobile, sex, birthyear, birthmonth, birthday)
            .compose(SchedulerUtils.ioToMain())
    }

}
