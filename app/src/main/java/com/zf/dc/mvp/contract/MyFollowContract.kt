package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.CommendList
import com.zf.dc.mvp.bean.MyFollowBean
import com.zf.dc.mvp.bean.MyFollowList

interface MyFollowContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //商品关注列表
        fun getMyFollowSuccess(bean: MyFollowBean)

        //删除商品关注
        fun delCollectGoods(msg: String)

        //猜你喜欢商品列表
        fun getLoveGoods(bean: List<CommendList>)

        fun freshEmpty()

        fun setFollowLoadMore(bean: List<MyFollowList>)

        fun setGoodsLoadMore(bean: List<CommendList>)

        fun setLoadFollowComplete()

        fun setLoadGoodsComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyFollow(page: Int?)

        fun requestDelCollectGoods(goods_id: String)

        fun requsetLoveGoods(type: String, page: Int?)

    }
}