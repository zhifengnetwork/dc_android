package com.zf.dc.ui.fragment.graphic

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.MyApplication
import com.zf.dc.R
import com.zf.dc.base.BaseFragment
import com.zf.dc.mvp.bean.AttriBute
import com.zf.dc.mvp.contract.GoodsAttrContract
import com.zf.dc.mvp.presenter.GoodsAttrPresenter
import com.zf.dc.ui.adapter.OrderInfoAdapter
import com.zf.dc.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_order_answer.*

class OrderAnswerFragment : BaseFragment(), GoodsAttrContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getGoodsAttr(bean: List<AttriBute>) {
        mAttr.clear()
        for (i in 0 until bean.size) {
            if (bean[i].attr.isNotEmpty()) {
                mAttr.add(bean[i])
            }
        }
        attrAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    private var mGoodsId = ""

    private val presenter by lazy { GoodsAttrPresenter() }

    //商品属性adapter
    private val attrAdapter by lazy { OrderInfoAdapter(context, mAttr) }

    //商品属性
    private var mAttr = ArrayList<AttriBute>()

    companion object {
        fun newInstance(id: String): OrderAnswerFragment {
            val fragment = OrderAnswerFragment()
            fragment.mGoodsId = id
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_order_answer

    override fun initView() {
        presenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                2,
                ContextCompat.getColor(context ?: MyApplication.context, R.color.colorLine)
            )
        )
        recyclerView.adapter = attrAdapter
    }

    override fun lazyLoad() {
        presenter.requestGoodsAttr(mGoodsId)
    }

    override fun initEvent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}