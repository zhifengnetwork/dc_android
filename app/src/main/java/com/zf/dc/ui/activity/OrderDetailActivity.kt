package com.zf.dc.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.dc.R
import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseActivity
import com.zf.dc.mvp.bean.OrderGoodsList
import com.zf.dc.mvp.bean.OrderListBean
import com.zf.dc.mvp.bean.WXPayBean
import com.zf.dc.mvp.contract.OrderDetailContract
import com.zf.dc.mvp.contract.OrderOperateContract
import com.zf.dc.mvp.contract.WXPayContract
import com.zf.dc.mvp.presenter.OrderDetailPresenter
import com.zf.dc.mvp.presenter.OrderOperatePresenter
import com.zf.dc.mvp.presenter.WXPayPresenter
import com.zf.dc.showToast
import com.zf.dc.ui.adapter.OrderGoodsAdapter
import com.zf.dc.utils.StatusBarUtils
import com.zf.dc.utils.TimeUtils
import com.zf.dc.utils.bus.RxBus
import com.zf.dc.view.popwindow.OrderPayPopupWindow
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.layout_detail_price.*
import kotlinx.android.synthetic.main.layout_en_order_address.*
import kotlinx.android.synthetic.main.layout_order_operation.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 订单详情
 */
class OrderDetailActivity : BaseActivity(), OrderDetailContract.View, OrderOperateContract.View, WXPayContract.View {


    //获取支付信息
    override fun setWXPay(bean: WXPayBean) {
        val api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, true)
        api.registerApp(UriConstant.WX_APP_ID)
        if (!api.isWXAppInstalled) {
            showToast("未安装微信")
        } else {
            val req = PayReq()
            req.appId = bean.appid
            req.partnerId = bean.partnerid
            req.prepayId = bean.prepayid
            req.packageValue = bean.packageValue
            req.nonceStr = bean.noncestr
            req.timeStamp = bean.timestamp
            req.sign = bean.sign
            api.sendReq(req)
        }
    }

    override fun showOperateError(msg: String, errorCode: Int) {

    }

    override fun setCancelOrder() {
    }

    override fun setConfirmReceipt() {
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    private var mOrderBean: OrderListBean? = null
    private val wxPayPresenter by lazy { WXPayPresenter() }

    @SuppressLint("SetTextI18n")
    override fun setOrderDetail(bean: OrderListBean) {

        mOrderBean = bean

        userName.text = bean.consignee
        userPhone.text = bean.mobile
        userAddress.text = "${bean.province}${bean.city}${bean.district}${bean.twon}${bean.address}"

        shopName.text = bean.store_name

        data.clear()
        data.addAll(bean.goods)
        adapter.notifyDataSetChanged()

        goodsPrice.text = "¥${bean.goods_price}" //商品总价
        shippingPrice.text = "¥${bean.shipping_price}" //运费
        orderPrice.text = "¥${bean.total_amount}" //订单总价

        shopDiscount.text = "-¥${bean.order_prom_amount}"
        creditPrice.text = "-¥${bean.integral_money}"
        signPrice.text = "-¥${bean.sign_price}"
        userMoney.text = "-¥${bean.user_money}"
        discountMoney.text = "-¥${bean.coupon_price}"

        orderNum.text = bean.order_sn
        createTime.text = TimeUtils.myOrderTime(bean.add_time)

        payType.text = bean.pay_name

        //订单状态 order_status
        when (bean.order_status) {
            //0：后台待确认 1：后台已确认
            //pay_status 0未支付 1已支付 2部分支付 3已退款 4拒绝退款
            "0", "1" -> {
                hideOperation()
                if (bean.pay_status == "0") {
//                    status.text = "待付款"
                    payNow.visibility = View.VISIBLE
                    cancelOrder.visibility = View.VISIBLE
//                    contactShop.visibility = View.VISIBLE
                } else if (bean.pay_status == "1") {
                    if (bean.shipping_status == "0") {
                        //未发货
//                        status.text = "待发货"
//                        remindSend.visibility = View.VISIBLE
                    } else if (bean.shipping_status == "1") {
                        //已发货
//                        status.text = "待收货"
                        shipping.visibility = View.VISIBLE
                        confirmReceive.visibility = View.VISIBLE
                    }
                }
            }
            //待评价(已收货)
            "2" -> {
                hideOperation()
//                status.text = "交易成功"
//                afterSale.visibility = View.VISIBLE
                evaluate.visibility = View.VISIBLE
            }
            //已取消
            "3" -> {
                hideOperation()
//                status.text = "已取消"
            }
            //已完成
            "4" -> {
                hideOperation()
//                status.text = "交易成功"
//                afterSale.visibility = View.VISIBLE
            }
        }

        //立即付款
        payNow.setOnClickListener {
            RxBus.getDefault().post(UriConstant.FRESH_CART, UriConstant.FRESH_CART)
            val window = object : OrderPayPopupWindow(
                this, R.layout.pop_order_pay,
                LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(320f), mOrderBean?.order_amount ?: "0"
            ) {}
            window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
            //取消支付
            window.onDismissListener = {
                start()
            }
            //确认支付
            window.onConfirmPayListener = {
                wxPayPresenter.requestWXPay(mOrderBean?.order_sn ?: "")
            }

        }

        //确认收货
        confirmReceive.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确认收货")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { _, _ ->
                    orderOperatePresenter.requestConfirmReceipt(mOrderId)
                }
                .show()
        }

        //物流
        shipping.setOnClickListener {
            ShippingActivity.actionStart(this, mOrderId)
        }

        //取消订单
        cancelOrder.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("取消该订单")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { _, _ ->

                    orderOperatePresenter.requestCancelOrder(mOrderId)
                }
                .show()
        }

        //去评价
        evaluate.setOnClickListener {
            EvaluateActivity.actionStart(this, bean)
        }

    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private fun hideOperation() {
        payNow.visibility = View.GONE
        checkCode.visibility = View.GONE
        remindSend.visibility = View.GONE
        contactShop.visibility = View.GONE
        cancelOrder.visibility = View.GONE
        payNow.visibility = View.GONE
        confirmReceive.visibility = View.GONE
        afterSale.visibility = View.GONE
        evaluate.visibility = View.GONE
    }

    companion object {
        private var mOrderId = ""
        fun actionStart(context: Context?, orderId: String) {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("orderId", orderId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener { finish() }
        titleName.text = "订单详情"
    }

    private val orderDetailPresenter by lazy { OrderDetailPresenter() }
    private val orderOperatePresenter by lazy { OrderOperatePresenter() }

    override fun layoutId(): Int = R.layout.activity_order_detail

    override fun initData() {
        mOrderId = intent.getStringExtra("orderId")
    }

    private val data = ArrayList<OrderGoodsList>()

    private val adapter by lazy { OrderGoodsAdapter(this, data) }

    override fun initView() {
        wxPayPresenter.attachView(this)
        orderOperatePresenter.attachView(this)
        orderDetailPresenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        arrowIcon.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        orderOperatePresenter.detachView()
        orderDetailPresenter.detachView()
        wxPayPresenter.detachView()
        super.onDestroy()
    }

    override fun initEvent() {
        RxBus.getDefault().subscribe<String>(this, UriConstant.WX_PAY_SUCCESS) {
            start()
        }
    }

    override fun start() {
        orderDetailPresenter.requestOrderDetail(mOrderId)
    }

}