package com.voltek.newsfeed.features.newssource.presentation

import com.voltek.newsfeed.base.presentation.adapter.AdapterItem

class NewsSourceUi(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val country: String,
        var isEnabled: Boolean
) : AdapterItem {
    override fun getUniqueId() = id.hashCode().toLong()
}
