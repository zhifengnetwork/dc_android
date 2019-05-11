package com.zf.dc.mvp.contract

import com.zf.dc.base.BaseBean
import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.ChangeUserBean
import okhttp3.MultipartBody

interface UpdateUserInfoContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)
        fun setHead(bean: BaseBean<String>)
        fun setChangeUserInfo(bean: BaseBean<ChangeUserBean>)
    }

    interface Presenter : IPresenter<View> {
        fun upLoadHead(head: MultipartBody.Part)
        fun changeUserInfo(
            nickname: String,
            mobile: String,
            sex: String,
            birthyear: String,
            birthmonth: String,
            birthday: String
        )
    }

}