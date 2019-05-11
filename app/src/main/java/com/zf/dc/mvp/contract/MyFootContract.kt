package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MyFootBean

interface MyFootContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //我的足迹
        fun getMyFoot(bean: List<MyFootBean>)

        //编辑足迹
        fun setMyFoot()

        //清空足迹
        fun clearMyFoot()

        fun freshEmpty()

        fun setLoadMore(bean: List<MyFootBean>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requesetMyFoot(page: Int?)

        fun requestsetMyFoot(visit_ids: String)

        fun requestclearMyFoot()
    }
}