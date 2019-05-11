package com.zf.dc.mvp.presenter


import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.ClassifyContract
import com.zf.dc.mvp.model.ClassifyModel
import com.zf.dc.net.exception.ExceptionHandle


class ClassifyPresenter : BasePresenter<ClassifyContract.View>(), ClassifyContract.Presenter {
    override fun requestClassify() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestClassify()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setTitle(it.data)
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


    private val model: ClassifyModel by lazy { ClassifyModel() }

}