package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.CommendContract
import com.zf.dc.mvp.model.CommendModel
import com.zf.dc.net.exception.ExceptionHandle

class CommendPresenter : BasePresenter<CommendContract.View>(), CommendContract.Presenter {

    private val model: CommendModel by lazy { CommendModel() }

    private var mPage = 1

    override fun requestCommend(type: String, page: Int?) {
        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestCommend(type, mPage)
                .subscribe({
                    mRootView?.apply {
                        when (it.status) {
                            0 -> {
                                if (mPage == 1) {
                                    if (it.data != null && it.data.goods_list.isNotEmpty()) {
                                        setRefreshCommend(it.data)
                                    } else {
                                        setEmpty()
                                    }
                                } else {
                                    if (it.data != null && it.data.goods_list.isNotEmpty()) {
                                        setLoadMoreCommend(it.data)
                                    } else {
                                        setLoadComplete()
                                    }
                                }
                                if (it.data != null && it.data.goods_list.isNotEmpty()) {
                                    if (it.data.goods_list.size < UriConstant.PER_PAGE) {
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

    //签到
    override fun requestAppSign() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAppSign()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    appSignSuccess(it.data)
                                }
                            }
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

    override fun requestMe() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestMe()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setMe(it.data)
                            }
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