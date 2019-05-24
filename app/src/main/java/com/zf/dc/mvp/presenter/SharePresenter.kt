package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.ShareContract
import com.zf.dc.mvp.model.ShareModel
import com.zf.dc.net.exception.ExceptionHandle

class SharePresenter : BasePresenter<ShareContract.View>(), ShareContract.Presenter {

    private val model: ShareModel by lazy { ShareModel() }

    override fun requestShare() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestShare()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setShare(it.data)
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