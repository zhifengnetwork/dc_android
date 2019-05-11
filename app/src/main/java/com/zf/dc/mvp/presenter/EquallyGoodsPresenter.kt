package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.EquallyGoodsContract
import com.zf.dc.mvp.model.EquallyGoodsModel
import com.zf.dc.net.exception.ExceptionHandle

class EquallyGoodsPresenter : BasePresenter<EquallyGoodsContract.View>(), EquallyGoodsContract.Presenter {
    private val model by lazy { EquallyGoodsModel() }
    private var mPage = 1
    override fun requestEquallyGoods(id: String, page: Int?, num: Int) {
        checkViewAttached()

        mPage = page ?: mPage

        mRootView?.showLoading()
        val disposable = model.getEquallyGoods(id, mPage, num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.goods_list.isNotEmpty()) {
                                        getEquallyGoods(it.data.goods_list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.goods_list)
                                }
                                if (it.data.goods_list.size < num) {
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