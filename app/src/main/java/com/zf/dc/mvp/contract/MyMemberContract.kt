package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MyMemberBean

interface MyMemberContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMyMember(bean: List<MyMemberBean>)

        fun freshEmpty()

        fun setLoadMore(bean: List<MyMemberBean>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyMember(page: Int?, next_user_id: String)
    }
}