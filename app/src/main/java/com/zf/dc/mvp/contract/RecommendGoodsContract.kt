package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.CommendList

interface RecommendGoodsContract {
    interface View : IBaseView {
        fun showError(message: String, errorCode: Int)

        fun getRecommendGoods(bean: List<CommendList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestRecommendGoods(id: String, sort_asc: String, page: Int)
    }

}