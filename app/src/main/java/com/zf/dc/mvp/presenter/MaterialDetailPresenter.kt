package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.MaterialDetailContract
import com.zf.dc.mvp.model.MaterialDetailModel
import com.zf.dc.net.exception.ExceptionHandle


class MaterialDetailPresenter : BasePresenter<MaterialDetailContract.View>(), MaterialDetailContract.Presenter {
    private val model by lazy { MaterialDetailModel() }
    override fun requestMaterialDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMaterialDetail(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMaterialDetail(it.data)
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