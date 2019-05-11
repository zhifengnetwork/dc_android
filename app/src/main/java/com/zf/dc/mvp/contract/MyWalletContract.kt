package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MyWalletBean

interface MyWalletContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getMyWallet(bean:MyWalletBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestMyWallet()
    }
}
