package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.MyWalletContract
import com.zf.dc.mvp.model.MyWalletModel
import com.zf.dc.net.exception.ExceptionHandle

class MyWalletPresenter : BasePresenter<MyWalletContract.View>(), MyWalletContract.Presenter {
    private val model by lazy { MyWalletModel() }
    override fun requestMyWallet() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMyWallet()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMyWallet(it.data)
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

}