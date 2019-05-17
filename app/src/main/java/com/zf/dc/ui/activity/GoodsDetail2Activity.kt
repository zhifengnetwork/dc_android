package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.dc.R
import com.zf.dc.api.UriConstant
import com.zf.dc.base.BaseActivity
import com.zf.dc.mvp.bean.TabEntity
import com.zf.dc.showToast
import com.zf.dc.ui.fragment.goodsdetail.EvaluationFragment
import com.zf.dc.ui.fragment.goodsdetail.GoodsDetailFragment
import com.zf.dc.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.pop_detail_share.view.*

class GoodsDetail2Activity : BaseActivity() {
    /**要传一个商品ID过来*/
    companion object {
        private var mIndex = 0
        fun actionStart(context: Context?, goods_id: String, actionId: String? = "", index: Int? = mIndex) {
            val intent = Intent(context, GoodsDetail2Activity::class.java)
            intent.putExtra("id", goods_id)
            intent.putExtra("actionId", actionId)
            intent.putExtra("index", index)
            context?.startActivity(intent)
        }
    }

    private var mTargetScene = SendMessageToWX.Req.WXSceneSession

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    override fun initToolBar() {

    }

    override fun layoutId(): Int = R.layout.activity_goods_detail

    private var id = ""

    private var mActionId = ""

    override fun initData() {
        id = intent.getStringExtra("id")
        mActionId = intent.getStringExtra("actionId")
        mIndex = intent?.getIntExtra("index", mIndex) ?: mIndex
    }

    override fun initView() {
        val fmgs =
            arrayListOf(
                GoodsDetailFragment.newInstance(id, mActionId) as Fragment,
                EvaluationFragment.newInstance(id) as Fragment
            )
        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("商品", 0, 0))
        entitys.add(TabEntity("评论", 0, 0))
        goods_tab.setTabData(entitys, this, R.id.goods_fl, fmgs)
        goods_tab.currentTab = mIndex
    }

    override fun initEvent() {
        //返回
        backLayout.setOnClickListener { finish() }

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
                            val api = WXAPIFactory.createWXAPI(this@GoodsDetail2Activity, UriConstant.WX_APP_ID, true)
                            // 将应用的appId注册到微信
                            api.registerApp(UriConstant.WX_APP_ID)
                            if (!api.isWXAppInstalled) {
                                showToast("未安装微信")
                            } else {
                                /** 发送文字类型 */
                                val webObj = WXWebpageObject()
                                webObj.webpageUrl =
                                    "https://mobile.zhifengwangluo.c3w.cc/Mobile/Goods/goodsInfo/id/$id.html"

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
    }

    override fun onResume() {
        super.onResume()
        mIndex = 0
    }

    override fun start() {

    }

}