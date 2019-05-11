package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.AdvertList
import com.zf.dc.mvp.bean.SecKillList

interface SecKillListContract {

    interface View : IBaseView {
        //广告图
        fun getAdList(bean: List<AdvertList>)

        fun setSecKillList(bean: List<SecKillList>)

        fun setLoadMore(bean: List<SecKillList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillList(startTime: String, endTime: String, page: Int?)

        fun requestAdList(pid: String)
    }

}