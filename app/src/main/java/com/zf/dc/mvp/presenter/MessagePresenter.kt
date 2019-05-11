package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant.PER_PAGE
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.MessageContract
import com.zf.dc.mvp.model.MessageModel
import com.zf.dc.net.exception.ExceptionHandle

class MessagePresenter : BasePresenter<MessageContract.View>(), MessageContract.Presenter {
    private val model by lazy { MessageModel() }
    private var mPage = 1
    override fun requestMessage(page: Int?,type: String) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMessage(mPage, PER_PAGE, type)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMessage(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.list)
                                }
                                if (it.data.list.size < PER_PAGE) {
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