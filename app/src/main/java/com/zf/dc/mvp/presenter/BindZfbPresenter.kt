package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.BindZfbContract
import com.zf.dc.mvp.model.BindZfbModel
import com.zf.dc.net.exception.ExceptionHandle

class BindZfbPresenter : BasePresenter<BindZfbContract.View>(), BindZfbContract.Presenter {
    private val model by lazy { BindZfbModel() }
    override fun requestBindZfb(zfb_account: String, realname: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setBindZfb(zfb_account, realname)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> bindZfbSuccess(it.msg)
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