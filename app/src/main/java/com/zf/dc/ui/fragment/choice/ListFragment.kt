package com.zf.dc.ui.fragment.choice

import com.zf.dc.R
import com.zf.dc.base.BaseFragment

class ListFragment:BaseFragment(){
    companion object {
        fun getInstance():ListFragment{
            return ListFragment()
        }
    }
    override fun getLayoutId(): Int = R.layout.fragment_choice_list

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}