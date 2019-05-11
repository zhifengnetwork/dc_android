package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.MaterialClassifyContract
import com.zf.dc.mvp.model.MaterialClassifyModel
import com.zf.dc.net.exception.ExceptionHandle


class MaterialClassifyPresenter : BasePresenter<MaterialClassifyContract.View>(), MaterialClassifyContract.Presenter {
    private val model by lazy { MaterialClassifyModel() }
    override fun requestMaterialClassify() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMaterialClassify()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMaterialClassify(it.data.list)
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