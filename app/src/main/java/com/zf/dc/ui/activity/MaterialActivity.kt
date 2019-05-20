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
class MaterialActivity : BaseActivity(), MaterialClassifyContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getMaterialClassify(bean: List<MaterialClassifyList>) {
        val mFragment = ArrayList<MaterialFragment>()
        val titles = ArrayList<String>()
        repeat(bean.size) { i ->
            mFragment.add(MaterialFragment.buildFragment(bean[i].cat_id))
            titles.add(bean[i].cat_name)
        }
        val mAdapter = BaseFragmentAdapter(supportFragmentManager, mFragment, titles)
        material_vp.adapter = mAdapter
        material_tab.setupWithViewPager(material_vp)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

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

    private val presenter by lazy { MaterialClassifyPresenter() }

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

    }

    override fun initEvent() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestMaterialClassify()
    }

}