package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.BonusBean

interface CashOutContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun requestCashOutSuccess(msg: String)

        fun getBonus(bean: BonusBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestCashOut(paypwd:String,money:String,bank_name:String,bank_card:String,realname:String)

        fun requestBonus()
    }
}