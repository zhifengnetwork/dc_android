package com.zf.dc.ui.fragment.graphic

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.dc.R
import com.zf.dc.base.BaseFragment
import com.zf.dc.mvp.bean.AttriBute
import com.zf.dc.mvp.contract.GoodsAttrContract
import com.zf.dc.mvp.presenter.GoodsAttrPresenter
import com.zf.dc.ui.adapter.OrderInfoAdapter
import com.zf.dc.view.recyclerview.RecyclerViewDivider
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_graphic.*

class GraphicFragment : BaseFragment(), GoodsAttrContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getGoodsAttr(bean: List<AttriBute>) {
        mData.clear()
        for(i in 0..bean.size){
            if (bean[i].attr.isNotEmpty()){
                mData.add(bean[i])
            }
        }
        adapter.notifyDataSetChanged()

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    //接收详情信息
    companion object {
        fun newInstance(data: String?, id: String?): GraphicFragment {
            val fragment = GraphicFragment()
            val bundle = Bundle()
            bundle.putString("mData", data)
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_graphic

    private val adapter by lazy { OrderInfoAdapter(context, mData) }

    private val presenter by lazy { GoodsAttrPresenter() }

    private var mData = ArrayList<AttriBute>()
    //接收传递过来的ID
    private var id: String = ""
    //接收传递过来的H5
    private var htmlText: String = ""


    override fun initView() {
        presenter.attachView(this)

        htmlText = arguments?.getString("mData").toString()

        id = arguments?.getString("id").toString()

        RichText.initCacheDir(context)

        RichText.from(htmlText).into(textR)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                2,
                ContextCompat.getColor(context!!, R.color.colorLine)
            )
        )

    }


    override fun lazyLoad() {
    }

    override fun initEvent() {
        show.setOnClickListener {
            show.isSelected = !show.isSelected
            if (show.isSelected) {
                show.text = "收起"
                textR.visibility = View.VISIBLE
            } else {
                show.text = "展开"
                textR.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

    }
    override fun onStart() {
        super.onStart()
        presenter.requestGoodsAttr(id)
    }
}