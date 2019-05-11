package com.zf.dc.ui.fragment

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.MyApplication
import com.zf.dc.R
import com.zf.dc.base.BaseFragment
import com.zf.dc.mvp.bean.MaterialList
import com.zf.dc.mvp.contract.MaterialListContract
import com.zf.dc.mvp.presenter.MaterialListPresenter
import com.zf.dc.showToast
import com.zf.dc.ui.activity.MaterialDetailActivity
import com.zf.dc.ui.adapter.MaterialAdapter
import com.zf.dc.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_material.*

/**
 * 素材区
 * */
class MaterialFragment : BaseFragment(), MaterialListContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun getMaterialList(bean: List<MaterialList>) {
        refreshLayout.setEnableLoadMore(true)
        mData.clear()
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<MaterialList>) {
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
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

    private var mId = ""

    companion object {
        fun buildFragment(id: String): MaterialFragment {
            val fragment = MaterialFragment()
            fragment.mId = id
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_material

    private val presenter by lazy { MaterialListPresenter() }

    private var mData = ArrayList<MaterialList>()

    private val divider by lazy {
        RecyclerViewDivider(
            context,
            LinearLayoutManager.VERTICAL,
            2,
            ContextCompat.getColor(context ?: MyApplication.context, R.color.colorBackground)
        )
    }

    private val mAdapter by lazy { MaterialAdapter(context, mData) }

    override fun initView() {
        presenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = mAdapter

    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestMaterialList(mId, 1)
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestMaterialList(mId, null)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            lazyLoad()

        }
        mAdapter.mClickListener = {
            MaterialDetailActivity.actionStart(context, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}