package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.CommonBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class EvaluateModel {


    fun requestEvaluate(info: String, orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestEvaluate(info, orderId)
            .compose(SchedulerUtils.ioToMain())
    }


    fun requestUploadImg(partList: MultipartBody.Part): Observable<BaseBean<CommonBean>> {
        return RetrofitManager.service.uploadCommonImg(partList)
            .compose(SchedulerUtils.ioToMain())
    }

}