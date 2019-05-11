package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.ShippingContract
import com.zf.dc.mvp.model.ShippingModel
import com.zf.dc.net.exception.ExceptionHandle

class ShippingPresenter : BasePresenter<ShippingContract.View>(), ShippingContract.Presenter {

    private val model: ShippingModel by lazy { ShippingModel() }

    override fun requestShipping(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestShipping(orderId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setShipping(it.data)
                            }else{
                                setEmpty()
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