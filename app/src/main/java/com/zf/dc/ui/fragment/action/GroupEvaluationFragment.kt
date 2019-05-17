package com.zf.dc.ui.fragment.action

import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.dc.R
import com.zf.dc.base.BaseFragment
import com.zf.dc.base.BaseFragmentAdapter
import com.zf.dc.mvp.bean.CommentNum
import com.zf.dc.mvp.bean.GoodEvaBean
import com.zf.dc.mvp.bean.GoodEvaList
import com.zf.dc.mvp.contract.GoodEvaContract
import com.zf.dc.mvp.presenter.GoodEvaPresenter
import com.zf.dc.net.exception.ErrorStatus
import com.zf.dc.showToast
import com.zf.dc.ui.adapter.EvaluationAdapter
import com.zf.dc.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_evaluation_detail.*

/**
 * 评价fragment
 */
class GroupEvaluationFragment : BaseFragment(), GoodEvaContract.View {

    //刷新数量
    private fun freshTitleNum(numBean: CommentNum) {
        mTitleAdapter?.let { it ->
            for (i in 0 until it.count) {
                it.setPageTitle(0, "全部\n" + numBean.total_sum)
                it.setPageTitle(1, "好评\n" + numBean.high_sum)
                it.setPageTitle(2, "中评\n" + numBean.center_sum)
                it.setPageTitle(3, "差评\n" + numBean.low_sum)
                it.setPageTitle(4, "晒单\n" + numBean.img_sum)
            }
            mTitleAdapter?.notifyDataSetChanged()
        }
    }

    override fun setGoodEva(bean: GoodEvaBean) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean.commentlist)
        adapter.notifyDataSetChanged()
        freshTitleNum(bean.comment_fr)
    }

    override fun setLoadMore(bean: GoodEvaBean) {
        data.addAll(bean.commentlist)
        adapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mGoodId = ""
    private var mType = 0
    private var mTitleAdapter: BaseFragmentAdapter? = null
    private var mTabLayout: TabLayout? = null
    private var mViewPager: ViewPager? = null

    companion object {
        fun newInstance(goodId: String, type: Int, titleAdapter: BaseFragmentAdapter, tabLayout: TabLayout, viewPager: ViewPager): GroupEvaluationFragment {
            val fragment = GroupEvaluationFragment()
            fragment.mGoodId = goodId
            fragment.mType = type
            fragment.mTitleAdapter = titleAdapter
            fragment.mTabLayout = tabLayout
            fragment.mViewPager = viewPager
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_evaluation_detail

    private val adapter by lazy { EvaluationAdapter(context, data) }

    private val evaPresenter by lazy { GoodEvaPresenter() }

    private val data = ArrayList<GoodEvaList>()

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        evaPresenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(divider)
    }

    private val divider by lazy {
        RecyclerViewDivider(
                context,
                LinearLayout.VERTICAL,
                DensityUtil.dp2px(1f),
                ContextCompat.getColor(context!!, R.color.colorBackground)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        evaPresenter.detachView()
    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        evaPresenter.requestGoodEva(mGoodId, mType, 1)
    }

    override fun initEvent() {

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            evaPresenter.requestGoodEva(mGoodId, mType, null)
        }
    }
}