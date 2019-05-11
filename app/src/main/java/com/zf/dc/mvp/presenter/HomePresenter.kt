package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.HomeContract
import com.zf.dc.mvp.model.HomeModel
import com.zf.dc.net.exception.ExceptionHandle

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private val model: HomeModel by lazy { HomeModel() }

    override fun requestHome() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestHome()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setHome(it.data)
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