package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.WXPayContract
import com.zf.dc.mvp.model.WXPayModel
import com.zf.dc.net.exception.ExceptionHandle

class WXPayPresenter : BasePresenter<WXPayContract.View>(), WXPayContract.Presenter {

    private val model: WXPayModel by lazy { WXPayModel() }

    override fun requestWXPay(order_sn: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestWXPay(order_sn)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()

                        when (it.status) {
                            0 -> if (it.data != null) {
                                setWXPay(it.data)
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