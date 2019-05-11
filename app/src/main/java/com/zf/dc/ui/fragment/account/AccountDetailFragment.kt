package com.zf.dc.ui.fragment.account

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.R
import com.zf.dc.base.BaseFragment
import com.zf.dc.mvp.bean.AccountDetailList
import com.zf.dc.mvp.contract.AccountDetailContract
import com.zf.dc.mvp.presenter.AccountDetailPresenter
import com.zf.dc.net.exception.ErrorStatus
import com.zf.dc.showToast
import com.zf.dc.ui.adapter.AccountDetailAdapter
import kotlinx.android.synthetic.main.fragment_account_details.*

class AccountDetailFragment : BaseFragment(), AccountDetailContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    //得到第一页数据
    override fun getAccountDetail(bean: List<AccountDetailList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        mData.clear()
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    //当第一页数据为空时
    override fun freshEmpty() {
        Log.e("检测","第一页数据为空")
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    //不是第一页获得数据
    override fun setLoadMore(bean: List<AccountDetailList>) {
        Log.e("检测","第2页数据 ")
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    //实际获得数据小于一页最大数据时
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    //下拉加载错误
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setBuyError(msg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mType = ""

    companion object {
        const val ALL = "all"
        const val PLUS = "plus"
        const val MINUS = "minus"
        fun newInstance(type: String): AccountDetailFragment {
            val fragment = AccountDetailFragment()
            fragment.mType = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_account_details

    private val mAdapter by lazy { AccountDetailAdapter(context, mData) }

    private val presenter by lazy { AccountDetailPresenter() }

    private var mData = ArrayList<AccountDetailList>()

    override fun initView() {
        presenter.attachView(this)
        mLayoutStatusView = multipleStatusView

        detail_rv.layoutManager = LinearLayoutManager(context)
        detail_rv.adapter = mAdapter
    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestAccountDetail(mType, 1, 6)
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestAccountDetail(mType, null, 6)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }



}