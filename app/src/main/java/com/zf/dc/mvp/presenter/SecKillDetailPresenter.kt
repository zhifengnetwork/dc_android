package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.SecKillDetailContract
import com.zf.dc.mvp.model.SecKillDetailModel
import com.zf.dc.net.exception.ExceptionHandle

class SecKillDetailPresenter : BasePresenter<SecKillDetailContract.View>(), SecKillDetailContract.Presenter {

    private val model: SecKillDetailModel by lazy { SecKillDetailModel() }

    override fun requestSecKillDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getSecKillDetail(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setSecKillDetail(it.data)
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