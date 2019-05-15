package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.GroupBuyList

interface GroupBuyContract {

    interface View : IBaseView {


        fun setGroupBuy(bean: List<GroupBuyList>)

        fun setLoadMoreGroupBuy(bean: List<GroupBuyList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestGroupBuy(type: String, page: Int?)

    }

}