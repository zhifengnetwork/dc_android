package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.OrderOperateContract
import com.zf.dc.mvp.model.OrderOperateModel
import com.zf.dc.net.exception.ExceptionHandle

class OrderOperatePresenter : BasePresenter<OrderOperateContract.View>(), OrderOperateContract.Presenter {

    private val model: OrderOperateModel by lazy { OrderOperateModel() }

    //确认收货
    override fun requestConfirmReceipt(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestConfirmReceipt(orderId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setConfirmReceipt()
                            else -> showOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun requestCancelOrder(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestCancelOrder(orderId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setCancelOrder()
                            else -> showOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

}