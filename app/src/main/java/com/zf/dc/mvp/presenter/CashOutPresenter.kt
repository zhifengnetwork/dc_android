package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.CashOutContract
import com.zf.dc.mvp.model.CashOutModel
import com.zf.dc.net.exception.ExceptionHandle

class CashOutPresenter : BasePresenter<CashOutContract.View>(), CashOutContract.Presenter {
    override fun requestBonus() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getBonus()
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getBonus(it.data)
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

    private val model by lazy { CashOutModel() }
    override fun requestCashOut(paypwd: String, money: String, bank_name: String, bank_card: String, realname: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestCashOut(paypwd, money, bank_name, bank_card, realname)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> requestCashOutSuccess(it.msg)
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