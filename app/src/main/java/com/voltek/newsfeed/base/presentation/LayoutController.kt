package com.voltek.newsfeed.base.presentation

import android.support.annotation.LayoutRes

interface LayoutController {

    @LayoutRes
    fun getLayout(): Int
}
