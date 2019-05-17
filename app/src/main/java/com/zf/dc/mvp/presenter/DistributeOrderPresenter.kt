package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.DistributeOrderContract
import com.zf.dc.mvp.model.DistributeOrderModel
import com.zf.dc.net.exception.ExceptionHandle

class DistributeOrderPresenter : BasePresenter<DistributeOrderContract.View>(), DistributeOrderContract.Presenter {

    private val model: DistributeOrderModel by lazy { DistributeOrderModel() }

    private var mPage = 1

    override fun requestDistributeOrder(page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestDistributeOrder(mPage)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (mPage == 1) {
                                if (it.data != null && it.data.list.isNotEmpty()) {
                                    setDistributeOrder(it.data.list)
                                } else {
                                    setEmpty()
                                }
                            } else {
                                if (it.data != null && it.data.list.isNotEmpty()) {
                                    setLoadMore(it.data.list)
                                } else {
                                    setLoadComplete()
                                }
                            }
                            if (it.data != null && it.data.list.isNotEmpty()) {
                                if (it.data.list.size < UriConstant.PER_PAGE) {
                                    setLoadComplete()
                                }
                            }
                            mPage += 1
                        }
                        else -> if (mPage == 1) {
                            showError(it.msg, it.status)
                        } else {
                            loadMoreError(it.msg, it.status)
                        }
                    }
                    dismissLoading()
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    if (mPage == 1) {
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    } else {
                        loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                }
            })
        addSubscription(disposable)
    }

}