package com.zf.dc.utils

import android.content.Context
import android.widget.ImageView
import com.zf.dc.GlideApp


class GlideUtils {

    companion object {
        fun loadUrlImage(context: Context?, url: String?, imageView: ImageView) {
            context?.let {
                url?.let { url ->
                    GlideApp.with(it)
                        .asBitmap()
                        .load(url)
                        .into(imageView)
                }

            }
        }
    }

}
