package com.voltek.newsfeed.common.presentation.base

import android.support.annotation.LayoutRes

interface LayoutController {

    @LayoutRes
    fun getLayout(): Int
}
