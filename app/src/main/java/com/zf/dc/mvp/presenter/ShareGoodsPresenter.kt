package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant.PER_PAGE
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.ShareGoodsContract
import com.zf.dc.mvp.model.ShareGoodsModel
import com.zf.dc.net.exception.ExceptionHandle

class ShareGoodsPresenter : BasePresenter<ShareGoodsContract.View>(), ShareGoodsContract.Presenter {
    private val model by lazy { ShareGoodsModel() }
    private var mPage = 1
    override fun requestShareGoods(is_distribut: Int, page: Int?) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getShareGoods(is_distribut, mPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.goods_list.isNotEmpty()) {
                                        getShareGoods(it.data.goods_list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.goods_list)
                                }
                                if (it.data.goods_list.size < PER_PAGE) {
                                    setLoadComplete()
                                }
                                mPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                    dismissLoading()
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}