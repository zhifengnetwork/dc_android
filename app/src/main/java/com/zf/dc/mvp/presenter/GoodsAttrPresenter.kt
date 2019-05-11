package com.zf.dc.mvp.presenter

import com.zf.dc.base.BasePresenter
import com.zf.dc.mvp.contract.GoodsAttrContract
import com.zf.dc.mvp.model.GoodsAttrModel
import com.zf.dc.net.exception.ExceptionHandle

class GoodsAttrPresenter : BasePresenter<GoodsAttrContract.View>(), GoodsAttrContract.Presenter {

    private val model by lazy { GoodsAttrModel() }
    override fun requestGoodsAttr(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsAttr(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsAttr(it.data.goods_attribute)
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