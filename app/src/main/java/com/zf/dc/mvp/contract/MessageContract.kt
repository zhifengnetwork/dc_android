package com.zf.dc.mvp.contract

import com.zf.dc.base.IBaseView
import com.zf.dc.base.IPresenter
import com.zf.dc.mvp.bean.MessageList

interface MessageContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getMessage(bean: List<MessageList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<MessageList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }
    interface Presenter:IPresenter<View>{
          fun requestMessage(page:Int?,type:String)
    }
}