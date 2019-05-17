package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.DetailRecordList

interface DetailRecordContract {

    interface View : IBaseView {


        fun setDetailRecord(bean: List<DetailRecordList>)

        fun setLoadMore(bean: List<DetailRecordList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestDetailRecord(page: Int?)

    }

}