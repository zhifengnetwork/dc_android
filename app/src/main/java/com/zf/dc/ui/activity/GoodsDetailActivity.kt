package com.zf.dc.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.dc.MyApplication.Companion.context
import com.zf.dc.R
import com.zf.dc.api.UriConstant
import com.zf.dc.api.UriConstant.BASE_URL
import com.zf.dc.base.BaseActivity
import com.zf.dc.mvp.bean.*
import com.zf.dc.mvp.contract.GoodsDetailContract
import com.zf.dc.mvp.presenter.GoodsDetailPresenter
import com.zf.dc.showToast
import com.zf.dc.ui.adapter.DetailEvaAdapter
import com.zf.dc.ui.adapter.GoodsDetailAdapter
import com.zf.dc.ui.adapter.GoodsSpecsAdapter
import com.zf.dc.ui.adapter.GuideAdapter
import com.zf.dc.ui.fragment.graphic.GraphicFragment
import com.zf.dc.ui.fragment.same.DetailSameFragment
import com.zf.dc.utils.GlideUtils
import com.zf.dc.utils.LogUtils
import com.zf.dc.utils.StatusBarUtils
import com.zf.dc.utils.TimeUtils
import com.zf.dc.utils.bus.RxBus
import com.zf.dc.view.popwindow.RegionPopupWindow
import com.zf.dc.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_goods_detail2.*
import kotlinx.android.synthetic.main.layout_buy.*
import kotlinx.android.synthetic.main.layout_detail_eva.*
import kotlinx.android.synthetic.main.layout_detail_goods.*
import kotlinx.android.synthetic.main.layout_detail_head.*
import kotlinx.android.synthetic.main.layout_detail_same.*
import kotlinx.android.synthetic.main.pop_detail_address.view.*
import kotlinx.android.synthetic.main.pop_detail_share.view.*
import kotlinx.android.synthetic.main.pop_detail_specs.view.*

/**
 * 商品详情
 */
class GoodsDetailActivity : BaseActivity(), GoodsDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //获得商品详情
    override fun getGoodsDetail(bean: GoodsDetailBean) {
        mData = bean
        images.clear()
        loveGoods.clear()
        loveGoods.addAll(bean.goods.seller_info.goods)
        //判断是否为秒杀商品
        if (mData?.goods?.prom_type == "1" || mActionId != "") {
            presenter.requestSecKillDetail(mActionId)
        }
        //轮播图获取 给图片加头部
        for (i in 0 until bean.goods.goods_images.size) {
            images.add(BASE_URL + bean.goods.goods_images[i])
        }
        //加载轮播图
        initBanner()
        //加载界面数据
        loadData()
        //相似推荐
        initSame()
        //图文详情
        initGraphic()

    }

    //秒杀商品详情
    override fun setSecKillDetail(bean: SecKillDetailBean) {
        nData = bean.info
        loadSecKill()

    }

    //获得商品评论（默认15条 全部 第一页）
    override fun setGoodEva(bean: List<GoodEvaList>) {
        mEva.clear()
        mEva.addAll(bean)
        evaAdapter.notifyDataSetChanged()
    }

    //获得地址列表
    @SuppressLint("SetTextI18n")
    override fun getAddress(bean: List<AddressBean>) {
        mAddress.clear()
        mAddress.addAll(bean)
        popAdapter.notifyDataSetChanged()
        //默认地址 邮费
        for (i in 0 until mAddress.size) {
            if (mAddress[i].is_default == "1") {
                goods_address.text = mAddress[i].province_name + mAddress[i].city_name + mAddress[i].district_name
                addressId = mAddress[i].address_id
                //邮费请求
                presenter.requestGoodsFreight(goodsID, mAddress[i].city, "1")
            }
        }

    }

    //获得商品运费
    override fun getGoodsFreight(bean: GoodsFreightBean) {
        if (bean.freight == "0.00" || bean.freight == "0") fare.text = "免邮费" else fare.text = bean.freight
    }

    //获得商品规格
    override fun getGoodsSpce(bean: List<List<GoodsSpecBean>>) {
        mSpec.clear()
        mSpec.addAll(bean)
        specsAdapter.notifyDataSetChanged()

    }

    //点击关注商品
    override fun setCollectGoods(msg: String) {
        showToast(msg)
    }

    //点击取消关注商品
    override fun delCollectGoods(msg: String) {
        showToast(msg)
    }

    //加入购物车
    override fun addCartSuccess(msg: String) {
        showToast(msg)
        specsPopWindow.onDismiss()
        cart.isChecked = true
        RxBus.getDefault().post(UriConstant.FRESH_CART, UriConstant.FRESH_CART)
    }

    //根据规格key获取图片，库存
    override fun getPricePic(bean: GoodsSpecInfo) {
        mPrice = bean
        specsPopWindow.updata()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    private var mActionId = ""

    /**要传一个商品ID过来*/
    companion object {
        const val FRESH_ORDER = "FRESH"
        fun actionStart(context: Context?, goods_id: String, actionId: String? = "") {
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra("id", goods_id)
            intent.putExtra("actionId", actionId)
            context?.startActivity(intent)
        }
    }

    private var mTargetScene = SendMessageToWX.Req.WXSceneSession

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }


    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        //分享
        shareLayout.setOnClickListener {

            val popUpWindow = object : ServicePopupWindow(
                this, R.layout.pop_detail_share,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    contentView.apply {
                        weChat.setOnClickListener {
                            val api = WXAPIFactory.createWXAPI(this@GoodsDetailActivity, UriConstant.WX_APP_ID, true)
                            // 将应用的appId注册到微信
                            api.registerApp(UriConstant.WX_APP_ID)
                            if (!api.isWXAppInstalled) {
                                showToast("未安装微信")
                            } else {
                                /** 发送文字类型 */
                                val webObj = WXWebpageObject()
                                webObj.webpageUrl =
                                    "https://mobile.zhifengwangluo.c3w.cc/Mobile/Goods/goodsInfo/id/" + mData?.goods?.goods_id + ".html"

                                val msg = WXMediaMessage()
                                msg.mediaObject = webObj //消息对象
                                msg.title = "智丰商城" //标题
                                msg.description = "" //描述

                                val req = SendMessageToWX.Req()
                                req.transaction = buildTransaction("text")
                                req.message = msg
                                req.scene = mTargetScene
                                api.sendReq(req)
                            }
                            onDismiss()

                        }

                        cancel.setOnClickListener {
                            onDismiss()
                        }
                    }
                }
            }
            popUpWindow.showAtLocation(shareLayout, Gravity.BOTTOM, 0, 0)
        }


        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_goods_detail2

    //网络请求
    private val presenter by lazy { GoodsDetailPresenter() }
    //商品详情
    private var mData: GoodsDetailBean? = null
    //秒杀商品详情
    private var nData: SecKillInfo? = null
    //商品评论
    private var mEva = ArrayList<GoodEvaList>()
    //商品规格
    private var mSpec = ArrayList<List<GoodsSpecBean>>()
    //店铺推荐商品
    private var loveGoods = ArrayList<GoodsList>()
    //详细商品规格信息
    private var mPrice: GoodsSpecInfo? = null
    //地址pop弹窗
    private lateinit var addressPopWindow: RegionPopupWindow
    //商品规格pop弹窗
    private lateinit var specsPopWindow: RegionPopupWindow
    //pop地址适配器
    private val popAdapter by lazy { GoodsDetailAdapter(context, mAddress) }
    //pop商品规格适配器
    private val specsAdapter by lazy { GoodsSpecsAdapter(context, mSpec) }
    //第一次打开pop商品规格默认请求
    private var no_off = true
    //接收地址列表
    private var mAddress = ArrayList<AddressBean>()
    //商品评价adapter
    private val evaAdapter by lazy { DetailEvaAdapter(this, mEva) }
    //Banner轮播图
    private var images = ArrayList<String>()
    //接收传递过来的id
    private var goodsID = ""
    //传递过来的规格ID
    private var itemId = ""
    //传递过来的规格名字
    private var itemName = ""
    //判断是点击加入购物车还是立即购买
    private var isChoice = true
    //记录用户选择的地址ID
    private var addressId = ""

    override fun initData() {
        goodsID = intent.getStringExtra("id")
        mActionId = intent.getStringExtra("actionId")
    }

    override fun initView() {
        presenter.attachView(this)


        floatingButton.hide()

        //全部评价
        allEvaluation.setOnClickListener {
            EvaluationActivity.actionStart(this, mData?.goods?.goods_id)
        }

        //banner
//        initBanner()

        //商品评价
        initEvaluation()
//
//        问大家
//        initAsk()

////        商家品牌推荐
//        initBrand()

//        相似推荐
        initSame()

        //图文详情
//        initGraphic()

    }


    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    private fun initGraphic() {

//        val titles = arrayOf("图文详情", "答疑")
//        val fgms = arrayListOf(
//            GraphicFragment.newInstance(mData?.goods_content, mData?.goods?.goods_id) as Fragment,
//            OrderAnswerFragment.newInstance() as Fragment
//        )

        val titles = arrayOf("图文详情")
        val fgms = arrayListOf(
                GraphicFragment.newInstance(mData?.goods_content, mData?.goods?.goods_id) as Fragment
        )
        segmentTabLayout.setTabData(titles, this, R.id.graphicFragment, fgms)
//        val titles = arrayOf("图文详情")
//        val fgms = arrayListOf(GraphicFragment.newInstance(mData?.goods_content, mData?.goods?.goods_id) as Fragment)
//        segmentTabLayout.setTabData(titles, this, R.id.graphicFragment, fgms)
    }


    private fun initEvaluation() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        evaRecyclerView.layoutManager = manager
        evaRecyclerView.adapter = evaAdapter
    }

//    private val askAdapter by lazy { DetailAskAdapter(this) }
//
//    private fun initAsk() {
////        askRecyclerView.layoutManager = LinearLayoutManager(this)
////        askRecyclerView.adapter = askAdapter
//    }
//
//    private val brandAdapter by lazy { DetailBrandAdapter(this, loveGoods) }
//    /**店铺商品列表*/
////    private fun initBrand() {
////        val manager = LinearLayoutManager(this)
////        manager.orientation = LinearLayoutManager.HORIZONTAL
////        brandRecyclerView.layoutManager = manager
////        brandRecyclerView.adapter = brandAdapter
////    }

    /**
     * 相似推荐
     * pageRecyclerView
     */
    private fun initSame() {
        val fgms = arrayListOf(
            DetailSameFragment.newInstance(mData?.goods?.cat_id, DetailSameFragment.BUY) as Fragment
            , DetailSameFragment.newInstance(mData?.goods?.cat_id, DetailSameFragment.SELL) as Fragment
        )
        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("相似推荐", 0, 0))
        entitys.add(TabEntity("同类热销排行", 0, 0))
        sameTabLayout.setTabData(entitys, this, R.id.frameLayout, fgms)

    }

    private fun initBanner() {

        val imageViews = ArrayList<ImageView>()
        repeat(images.size) { pos ->
            val img = ImageView(this)

            Glide.with(this).load(images[pos]).into(img)

            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${images.size}"

        /** 滑动改变指示器 */
        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${images.size}"
            }
        })
    }

    override fun initEvent() {

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            //标题栏渐变
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            orderDetailHead.setBackgroundColor(
                changeAlpha(
                    ContextCompat.getColor(this, R.color.whit)
                    , alpha
                )
            )
            //回到顶部按钮
            if (scrollY - oldScrollY > 0) {
                floatingButton.hide()
            } else {
                floatingButton.show()
            }

        }

        floatingButton.setOnClickListener {
            scrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP)
        }

        /**选择配送地址*/
        goods_address.setOnClickListener {
            addressPopWindow = object : RegionPopupWindow(
                this, R.layout.pop_detail_address,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {

                    contentView?.apply {
                        pop_recyclerView.layoutManager = LinearLayoutManager(context)
                        pop_recyclerView.adapter = popAdapter
                        //将所选的的地址信息传递过来/计算出邮费
                        popAdapter.mClickListener = {
                            goods_address.text = it.province_name + it.city_name + it.district_name
                            //记录所选地址id
                            addressId = it.address_id
                            //邮费请求
                            presenter.requestGoodsFreight(goodsID, it.city, "1")
                            //关闭弹窗
                            addressPopWindow.onDismiss()
                        }
                        //新增地址
                        add_tv.setOnClickListener {
                            AddressEditActivity.actionStart(context, null)
                        }
                    }
                }
            }
            //更新视图
//            addressPopWindow.updata()
            addressPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }

        //收藏按钮
        collect.setOnClickListener {
            if (collect.isChecked) {
                presenter.requestCollectGoods(mData?.goods?.goods_id.toString())

            } else {
                presenter.requestDelCollectGoods(mData?.goods?.goods_id.toString())
            }
        }

        /**加入购物车*/
        shop_cat.setOnClickListener {
            isChoice = true
            popWindow()
        }
        /**立即购买*/
        shop_buy.setOnClickListener {
            isChoice = false
            popWindow()
        }
        /**购物车复选框*/
        cart.setOnClickListener {
            //选中亮图标 未选中在购物车删除该商品
            cart.isChecked = !cart.isChecked
            MainActivity.actionStart(this, 2)
        }

        /**商品详情-相似推荐*/
//        RxBus.getDefault().subscribe<String>(this, FRESH_ORDER) {
//            //请求商品详情
//            presenter.requestGoodsDetail(it)
//            //请求评论
//            presenter.requestGoodEva(it, 1, 1, 6)
//            //请求规格
//            presenter.requestGoodsSpec(it)
//            //回到顶部
//            scrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP)
//        }

    }

    override fun onResume() {
        super.onResume()
        //请求地址列表
        presenter.requestAddress()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        //请求商品详情
        presenter.requestGoodsDetail(goodsID)
        //请求评论
        presenter.requestGoodEva(goodsID, 1, 1, 6)
        //请求规格
        presenter.requestGoodsSpec(goodsID)
        //请求地址列表
        presenter.requestAddress()

    }

    /**将网络请求到的数据赋值到页面上*/
    private fun loadData() {
        //商品名字
        goods_name.text = mData?.goods?.goods_name
        //商品价格
        shop_price.text = mData?.goods?.shop_price
        //市场价格
        market_price.text = "￥" + mData?.goods?.market_price
        //销量
        virtual_sales_sum.text = mData?.goods?.virtual_collect_sum
        //库存
        store_count.text = mData?.goods?.store_count + "件"
        //运费
        if (mData?.goods?.is_free_shipping == "1") fare.text = "免运费" else fare.text = "按实际地区收费"
        //说明
        goods_remark.text = mData?.goods?.goods_remark
        //商品评论数
        comments.text = "(${mData?.goods?.comment_count})"
        //好评率
        high_rate.text = mData?.goods?.comment_fr?.high_rate + "%"
        //问大家数

        //店铺名字
//        shopName.text = mData?.goods?.seller_info?.store_name
//        //店铺在售件数
//        inSellNum.text = mData?.goods?.seller_info?.num
//        //店铺图片avatar
//        GlideUtils.loadUrlImage(this, mData?.goods?.seller_info?.avatar, shopIcon)
        //是否已收藏
        collect.isChecked = mData?.goods?.is_collect != "0"
        //是否在购物车
        cart.isChecked = mData?.goods?.is_cart != "0"

    }

    /**pop弹窗 加入购物车 立即购买*/
    private fun popWindow() {
        specsPopWindow = object : RegionPopupWindow(
            this, R.layout.pop_detail_specs,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ) {
            override fun initView() {

                var sum = contentView?.card_number?.text.toString().toInt()
                //第一次打开默认请求第一个
                if (no_off) {
                    //重组ID
                    for (i in 0 until mSpec.size) {
                        itemId = if (i == 0) {
                            mSpec[i][0].id
                        } else {
                            itemId + "_" + mSpec[i][0].id
                        }
                        //名字
                        itemName += mSpec[i][0].item
                    }
                    no_off = if (itemId != "") {
                        itemName = "已选择:$itemName"
                        presenter.requestPricePic(itemId, mData?.goods?.goods_id.toString())
                        false
                    } else {
                        true
                    }
                }
                contentView?.apply {
                    specs_rl.layoutManager = LinearLayoutManager(context)
                    specs_rl.adapter = specsAdapter

                    del_btn.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.reduce))

                    //没有规格的商品 根据no_off判断
                    if (no_off) {
                        //图片
                        GlideUtils.loadUrlImage(
                            context,
                            "https://mobile.zhifengwangluo.c3w.cc" + mData?.goods?.original_img,
                            goods_img
                        )
                        //名称
//                            goodsName.text = mData?.goods?.goods_name
                        //价格
                        goods_price.text = mData?.goods?.shop_price
                        //规格
                        goods_stock.text = "请选择:数量"
                        pop_view.visibility = View.INVISIBLE
                    } else {
                        //图片
                        GlideUtils.loadUrlImage(context, mPrice?.spec_img, goods_img)
                        //名称
//                            goodsName.text = mData?.goods?.goods_name
                        //价格
                        goods_price.text = mPrice?.price
                        //规格
                        goods_stock.text = itemName

                        pop_view.visibility = View.VISIBLE
                    }
                    //输入文本框
                    card_number.addTextChangedListener {
                        val text = it.toString()
                        val len = it.toString().length
                        if (len > 1 && text.startsWith("0")) {
                            it?.replace(0, 1, "")
                        }
                        sum = if (text == "") {
                            card_number.setText("1")
                            1
                        } else {
                            text.toInt()
                        }
                        if (sum > 1) {
                            del_btn.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.reduce_b))
                        }

                    }
                    //点击减少数量
                    del_btn.setOnClickListener {
                        sum -= 1
                        if (sum <= 0) {
                            sum = 1
                        }
                        if (sum == 1) {
                            del_btn.setImageDrawable(
                                ContextCompat.getDrawable(
                                    applicationContext,
                                    R.drawable.reduce
                                )
                            )
                        }
                        card_number.setText(sum.toString())

                    }
                    //点击增加数量
                    add_btn.setOnClickListener {
                        sum += 1
                        card_number.setText(sum.toString())
                        del_btn.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.reduce_b))
                    }
                    /**确认按钮 加入购物车 立即购买*/
                    specs_btn.setOnClickListener {
                        if (isChoice) {
                            //商品ID 数量（默认1） 规格ID
                            presenter.requestAddCart(mData?.goods?.goods_id.toString(), sum, itemId)
                        } else {
                            /**判断用户是否已选配送地址*/
                            if (addressId == "") {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("你还没有选择收货地址")
                                builder.setMessage("是否去添加收货地址")
                                builder.setPositiveButton("是") { dialog, which ->
                                    AddressEditActivity.actionStart(context, null)
                                    dialog.dismiss()
                                }
                                builder.setNegativeButton("否") { dialog, which ->
                                    dialog.dismiss()
                                }
                                builder.show()
                            } else {
                                ConfirmOrderActivity.actionStart(
                                    context,
                                    0,
                                    "1",
                                    mData?.goods?.goods_id.toString(),
                                    sum.toString(),
                                    itemId,
                                    "",
                                    addressId = addressId
                                )
                            }
                            specsPopWindow.onDismiss()
                        }

                    }
                    //关闭弹窗
                    close_btn.setOnClickListener {
                        specsPopWindow.onDismiss()
                    }

                }
                //适配器监听回调
                specsAdapter.mClickListener = { id: String, name: String ->
                    itemId = id
                    itemName = "已选择:$name"
                    presenter.requestPricePic(itemId, mData?.goods?.goods_id.toString())
                }
            }

        }
        specsPopWindow.updata()
        specsPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
    }

    /**判断是否为秒杀商品*/
    private fun loadSecKill() {
        label.text = "秒杀"
        //倒计时
        label_ly.visibility = View.VISIBLE
        //秒杀价字体
        label_money.visibility = View.VISIBLE
        //商品价格
        shop_price.text = nData?.price
        //库存
        store_count.text = nData?.store_count + "件"
        //倒计时
        //当前时间戳
        val currentTime = System.currentTimeMillis()
        if (nData != null) {
            if (currentTime < nData!!.start_time * 1000) {
                val tickTime = (nData!!.start_time * 1000) - currentTime

                val countDownTimer = object : CountDownTimer(tickTime, 1000) {
                    override fun onFinish() {

                    }

                    override fun onTick(millisUntilFinished: Long) {
                        label_time.text = TimeUtils.getCountTime2(millisUntilFinished)
                    }

                }
                countDownTimer.start()
            } else {
                //过时，秒杀已到
                //倒计时
                label_ly.visibility = View.GONE
//                start()
            }
        }
    }
}