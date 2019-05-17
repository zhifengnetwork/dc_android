package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.RechargeRecordList

interface RechargeContract{
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getRechargeList(bean: List<RechargeRecordList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<RechargeRecordList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestRechargeList(page: Int?)
    }
}