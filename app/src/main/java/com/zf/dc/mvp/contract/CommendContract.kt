package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AppSignBean
import com.zf.dc.mvp.bean.CommendBean
import com.zf.dc.mvp.bean.MeBean

interface CommendContract {

    interface View : IBaseView {


        fun setRefreshCommend(bean: CommendBean)

        fun setLoadMoreCommend(bean: CommendBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
        //签到
        fun appSignSuccess(bean: AppSignBean)

        //我的页面
        fun setMe(bean: MeBean)

    }

    interface Presenter : IPresenter<View> {
        fun requestCommend(type: String, page: Int?)

        fun requestAppSign()

        fun requestMe()
    }

}