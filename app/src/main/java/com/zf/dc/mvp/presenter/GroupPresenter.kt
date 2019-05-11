package com.zf.dc.mvp.presenter

import com.zf.dc.api.UriConstant
import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.GroupContract
import com.zf.dc.mvp.model.GroupModel
import com.zf.dc.net.exception.ExceptionHandle

class GroupPresenter : BasePresenter<GroupContract.View>(), GroupContract.Presenter {

    private val model: GroupModel by lazy { GroupModel() }

    private var mPage = 1

    override fun requestGroup(page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGroup(mPage)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (mPage == 1) {
                                if (it.data != null && it.data.isNotEmpty()) {
                                    setGroup(it.data)
                                } else {
                                    freshEmpty()
                                }
                            } else {
                                if (it.data != null && it.data.isNotEmpty()) {
                                    loadMore(it.data)
                                } else {
                                    loadComplete()
                                }
                            }
                            if (it.data != null) {
                                if (it.data.size < UriConstant.PER_PAGE) {
                                    loadComplete()
                                }
                            }
                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    if (mPage == 1) showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    else loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}