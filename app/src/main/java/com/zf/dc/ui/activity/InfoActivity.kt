package com.zf.dc.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.zf.dc.R
import com.zf.dc.base.BaseActivity
import com.zf.dc.utils.StatusBarUtils
import com.zf.dc.view.ToolBarHelper
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : BaseActivity() {


    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, InfoActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, com.zf.dc.R.color.colorSecondText),
            0.3f
        )
    }

    override fun layoutId(): Int = R.layout.activity_info

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val host = supportFragmentManager.findFragmentById(R.id.infoFragment) as NavHostFragment
        val navController = host.navController

        /** 切换fragment更改标题 */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            ToolBarHelper.addMiddleTitle(this, destination.label, toolBar)
        }

        setSupportActionBar(toolBar)

        toolBar.run {
            title = ""
            navigationIcon = ContextCompat.getDrawable(this@InfoActivity, R.drawable.fanhui)
            setNavigationOnClickListener {
                if (!navController.navigateUp()) {
                    finish()
                } else {

                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.infoFragment).navigateUp()
    }


    override fun initData() {
    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun start() {
    }
}