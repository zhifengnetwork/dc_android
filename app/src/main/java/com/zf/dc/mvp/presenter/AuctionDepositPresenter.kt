package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.AuctionDepositContract
import com.zf.dc.mvp.model.AuctionDepositModel
import com.zf.dc.net.exception.ExceptionHandle

class AuctionDepositPresenter : BasePresenter<AuctionDepositContract.View>(), AuctionDepositContract.Presenter {

    private val model: AuctionDepositModel by lazy { AuctionDepositModel() }

    override fun requestDeposit(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestDeposit(id)
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