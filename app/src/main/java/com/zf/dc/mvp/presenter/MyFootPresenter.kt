package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant.PER_PAGE
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.MyFootContract
import com.zf.dc.mvp.model.MyFootModel
import com.zf.dc.net.exception.ExceptionHandle

class MyFootPresenter : BasePresenter<MyFootContract.View>(), MyFootContract.Presenter {
    private val model by lazy { MyFootModel() }
    private var mPage = 1
    override fun requesetMyFoot(page: Int?) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMyFoot(mPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.isNotEmpty()) {
                                        getMyFoot(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data)
                                }
                                if (it.data.size < PER_PAGE) {
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

    override fun requestsetMyFoot(visit_ids: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setMyFoot(visit_ids)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setMyFoot()
                        else -> showError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun requestclearMyFoot() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.clearMyFoot()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> clearMyFoot()
                        else -> showError(it.msg, it.status)
                    }
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