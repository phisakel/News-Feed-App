package com.voltek.newsfeed.features.newssource.presentation

import android.view.ViewGroup
import com.voltek.newsfeed.R
import com.voltek.newsfeed.base.presentation.adapter.AdapterItem
import com.voltek.newsfeed.base.presentation.adapter.BaseDelegate
import com.voltek.newsfeed.base.presentation.adapter.BaseViewHolder

class NewsSourceDelegate : BaseDelegate<NewsSourceUi, NewsSourceDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(parent)
    }

    override fun isForViewType(item: AdapterItem, items: MutableList<AdapterItem>, position: Int): Boolean {
        return item is NewsSourceUi
    }

    override fun onBindViewHolder(item: NewsSourceUi, viewHolder: ViewHolder, payloads: MutableList<Any>) {}

    class ViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.item_source)
}
