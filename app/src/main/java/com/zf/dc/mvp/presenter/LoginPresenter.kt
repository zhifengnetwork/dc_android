package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.LoginContract
import com.zf.dc.mvp.model.LoginModel
import com.zf.dc.net.exception.ExceptionHandle

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private val model: LoginModel by lazy { LoginModel() }

    override fun requestWeChatLogin(code: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestWeChatLogin(code)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setWeChatLogin(it.data)
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

    override fun requestLogin(phone: String, pwd: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.login(phone, pwd)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            loginSuccess(it.data)
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