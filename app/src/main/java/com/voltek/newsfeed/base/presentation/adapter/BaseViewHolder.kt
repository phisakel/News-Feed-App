package com.voltek.newsfeed.base.presentation.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.voltek.newsfeed.common.presentation.inflate
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder(
        parent: ViewGroup,
        @LayoutRes layoutId: Int
) : RecyclerView.ViewHolder(parent.inflate(layoutId)), LayoutContainer {
    override val containerView: View = itemView

    val context: Context = parent.context.applicationContext
}
