package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import okhttp3.MultipartBody

interface EvaluateContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setEvaluate()

        fun setUploadImg(url: String)

    }

    interface Presenter : IPresenter<View> {

        fun requestEvaluate(info: String, orderId: String)

        fun requestUploadImg(partList: MultipartBody.Part)
    }

}