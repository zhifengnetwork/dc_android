package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.base.BaseFragmentAdapter
import com.zf.dc.mvp.bean.MaterialClassifyList
import com.zf.dc.mvp.bean.MaterialList
import com.zf.dc.mvp.contract.MaterialClassifyContract
import com.zf.dc.mvp.contract.MaterialListContract
import com.zf.dc.mvp.presenter.MaterialClassifyPresenter
import com.zf.dc.mvp.presenter.MaterialListPresenter
import com.zf.dc.showToast
import com.zf.dc.ui.adapter.MaterialAdapter
import com.zf.dc.ui.fragment.MaterialFragment
import com.zf.dc.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 素材区
 * */
class MaterialActivity : BaseActivity(), MaterialListContract.View {
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

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MaterialActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text = "素材区"
        back.setOnClickListener { finish() }
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_material

    private val presenter by lazy { MaterialListPresenter() }

    private val divider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            2,
            ContextCompat.getColor(this, R.color.colorBackground)
        )
    }
    private var mData = ArrayList<MaterialList>()

    private val mAdapter by lazy { MaterialAdapter(this, mData) }

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = mAdapter
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestMaterialList("0", null)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            refreshLayout.setEnableLoadMore(false)
            presenter.requestMaterialList("0", 1)
        }
        mAdapter.mClickListener = {
            MaterialDetailActivity.actionStart(this, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestMaterialList("0", 1)
    }

}