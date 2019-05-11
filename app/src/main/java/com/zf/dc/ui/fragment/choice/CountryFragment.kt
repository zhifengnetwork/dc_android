package com.zf.dc.ui.fragment.choice

import com.zf.dc.R
import com.zf.dc.base.BaseFragment

class CountryFragment:BaseFragment(){

    companion object {
        fun getInstance():CountryFragment{
            return CountryFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_choice_country

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}