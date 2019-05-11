package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AccountDetailList

interface AccountDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getAccountDetail(bean: List<AccountDetailList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<AccountDetailList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }

    interface Presenter : IPresenter<View> {

        fun requestAccountDetail(type: String, page: Int?, num: Int)

    }
}