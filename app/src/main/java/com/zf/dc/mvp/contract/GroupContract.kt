package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.GroupBean

interface GroupContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)

        fun setGroup(bean: List<GroupBean>)

        fun loadMore(bean: List<GroupBean>)

        fun freshEmpty()

        fun loadComplete()
    }

    interface Presenter : IPresenter<View> {
        fun requestGroup(page: Int?)

    }

}