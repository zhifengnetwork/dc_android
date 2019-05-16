package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.DistributeContract
import com.zf.dc.mvp.model.DistributeModel
import com.zf.dc.net.exception.ExceptionHandle

class DistributePresenter : BasePresenter<DistributeContract.View>(), DistributeContract.Presenter {

    private val model: DistributeModel by lazy { DistributeModel() }

    override fun requestDistribute() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestDistribute()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setDistribute(it.data)
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