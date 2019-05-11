package com.zf.dc.ui.fragment

import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.dc.R
import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseFragment
import com.zf.dc.mvp.bean.*
import com.zf.dc.mvp.contract.CommendContract
import com.zf.dc.mvp.contract.HomeContract
import com.zf.dc.mvp.presenter.CommendPresenter
import com.zf.dc.mvp.presenter.HomePresenter
import com.zf.dc.showToast
import com.zf.dc.ui.activity.*
import com.zf.dc.ui.adapter.CommendAdapter
import com.zf.dc.ui.adapter.HomeSecKillAdapter
import com.zf.dc.utils.GlideImageLoader
import com.zf.dc.utils.TimeUtils
import com.zf.dc.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_top.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_prefecture.*
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_seckill.*

class HomeFragment : BaseFragment(), HomeContract.View, CommendContract.View {

    override fun setMe(bean: MeBean) {
    }

    override fun appSignSuccess(bean: AppSignBean) {

    }

    override fun setRefreshCommend(bean: CommendBean) {
        refreshLayout.setEnableLoadMore(true)
        commendData.clear()
        commendData.addAll(bean.goods_list)
        commendAdapter.notifyDataSetChanged()
    }

    override fun setLoadMoreCommend(bean: CommendBean) {
        commendData.addAll(bean.goods_list)
        commendAdapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun setHome(bean: HomeBean) {
        initBanner(bean.adlist)
        initArticle(bean.articlelist)
        initSecKill(bean.flash_sale_goods)
        //倒计时
        initCountDown(bean.end_time)
    }

    private fun initCountDown(time: Long) {
        countDownTime?.cancel()
        countDownTime = object : CountDownTimer(time * 1000, 1000) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                countDown.text = TimeUtils.getCountTime2(millisUntilFinished)
            }
        }
        countDownTime?.start()
    }

    private fun initSecKill(secKill: List<SecKillList>) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        secKillRecyclerView.layoutManager = layoutManager
        secKillRecyclerView.adapter = secKillAdapter
        secKillData.clear()
        secKillData.addAll(secKill)
        secKillAdapter.notifyDataSetChanged()
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    private val commendData = ArrayList<CommendList>()

    //推荐商品列表
    private val commendAdapter by lazy { CommendAdapter(context, commendData) }

    private var countDownTime: CountDownTimer? = null

    private val homePresenter by lazy { HomePresenter() }

    override fun onDestroyView() {
        countDownTime?.cancel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        homePresenter.detachView()
        commendPresenter.detachView()
        try {
            homeArticle?.stopAutoScroll()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        try {
            homeArticle?.startAutoScroll()
        } catch (e: Exception) {
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            homeArticle?.stopAutoScroll()
        } catch (e: Exception) {
        }
    }

    //头条数据
    private fun initArticle(article: List<ArticleList>) {
        val headList = ArrayList<String>()
        for (title in article) {
            headList.add(title.title)
        }
        homeArticle.setTextList(headList)

    }


    override fun initView() {
        homePresenter.attachView(this)
        commendPresenter.attachView(this)


        homeArticle.setText(14f, 5, Color.RED)
        homeArticle.setTextStillTime(4000) //停留时长
        homeArticle.setAnimTime(400) //进出间隔时间
        homeArticle.setOnItemClickListener { }
        homeArticle.startAutoScroll()

//        initBanner()
        //品质生活pager
//        initLife()
        //秒杀列表

        commendRecyclerView.layoutManager = GridLayoutManager(context, 2)
        commendRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(15f)))
        commendRecyclerView.adapter = commendAdapter


    }


    private fun initBanner(banner: List<AdList>) {
        // 在最后需要start, start()在点击事件之后
        val imgs = ArrayList<String>()
        for (img in banner) {
            imgs.add(UriConstant.BASE_URL + img.ad_code)
        }
        topBanner.setImageLoader(GlideImageLoader())
        topBanner.setImages(imgs)
        topBanner.start()

        topBanner.setOnBannerListener {
            if ((banner[it].goods_id ?: "").isNotEmpty()) {
                GoodsDetailActivity.actionStart(context, banner[it].goods_id ?: "")
            }
        }
    }

    private val secKillData = ArrayList<SecKillList>()
    private val secKillAdapter by lazy { HomeSecKillAdapter(context, secKillData) }

    private val commendPresenter by lazy { CommendPresenter() }

    override fun lazyLoad() {
        homePresenter.requestHome()
        refreshLayout.setEnableLoadMore(false)
        commendPresenter.requestCommend("is_recommend", 1)
    }

    override fun initEvent() {

        actionLayout.setOnClickListener {
            ActionActivity.actionStart(context, ActionActivity.AUCTION)
        }

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            commendPresenter.requestCommend("is_recommend", null)
        }

        home_nestedscroll.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            home_title.setBackgroundColor(
                    changeAlpha(
                            ContextCompat.getColor(context!!, R.color.head_bg)
                            , alpha
                    )
            )
        }

        //站内消息
        homeMessage.setOnClickListener {
            MessageActivity.actionStart(context)
        }

        //搜索
        searchLayout.setOnClickListener {
            SearchActivity.actionStart(context, "")
        }

        //秒杀
        action.setOnClickListener {
            ActionActivity.actionStart(context, ActionActivity.SEC_KILL)
        }

        //品质生活banner
//        qualityBanner.start()

        //精选
        choice.setOnClickListener {
            ChoiceActivity.actionStart(context)
        }
        //签到
        sign_btn.setOnClickListener {
            SignInGiftActivity.actionStart(context)
        }

    }

    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

}