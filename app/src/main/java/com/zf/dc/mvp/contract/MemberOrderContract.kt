package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MemberOrderList
import com.zf.dc.mvp.bean.MyMemberOrderBean

interface MemberOrderContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMenberOrder(bean: MyMemberOrderBean)

        fun freshEmpty()

        fun setLoadMore(bean: List<MemberOrderList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)


    }

    interface Presenter : IPresenter<View> {
        fun requestMemberOrder(page: Int?, next_user_id: String)
    }
}
