package com.zf.dc.ui.fragment.same

import com.zf.dc.R
import com.zf.dc.base.BaseFragment

/**
 * 订单详情的热销排行
 */
class DetailRankFragment : BaseFragment() {

    companion object {
        fun newInstance(): DetailRankFragment {
            return DetailRankFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_detail_rank

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}