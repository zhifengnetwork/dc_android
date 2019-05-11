package com.zf.dc.mvp.bean

data class CartCountBean(
        val id: String,
        val sum: Int,
//        val shopPosition: Int? = 0,
        val goodsPosition: Int? = 0
)