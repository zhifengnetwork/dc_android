package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.AppSignDayContract
import com.zf.dc.mvp.model.AppSignDayModel
import com.zf.dc.net.exception.ExceptionHandle

class AppSignDayPresenter : BasePresenter<AppSignDayContract.View>(), AppSignDayContract.Presenter {
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

    private val model by lazy { AppSignDayModel() }
    override fun requestAppSignDay() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAppSignDay()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getAppSignDay(it.data)
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