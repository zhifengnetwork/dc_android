package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant.PER_PAGE
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.CashOutRecordContract
import com.zf.dc.mvp.model.CashOutRecordModel
import com.zf.dc.net.exception.ExceptionHandle

class CashOutRecordPresenter : BasePresenter<CashOutRecordContract.View>(), CashOutRecordContract.Presenter {
    private var mPage = 1
    override fun requestCashOutList(page: Int?, num: Int) {
        checkViewAttached()

        mPage = page ?: mPage

        mRootView?.showLoading()
        val disposable = model.getCashOutRecordList(mPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getCashOutList(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.list)
                                }
                                if (it.data.list.size < PER_PAGE) {
                                    setLoadComplete()
                                }
                                mPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                    dismissLoading()
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    private val model by lazy { CashOutRecordModel() }
}