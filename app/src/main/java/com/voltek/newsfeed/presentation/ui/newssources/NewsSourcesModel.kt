package com.voltek.newsfeed.presentation.ui.newssources

import com.voltek.newsfeed.R
import com.voltek.newsfeed.presentation.base.BaseModelOld
import com.voltek.newsfeed.presentation.entity.SourceUI

class NewsSourcesModel(subscriber: (BaseModelOld) -> Unit) : BaseModelOld(subscriber) {

    var categoryId: Int = R.id.action_all

    var loading: Boolean = false

    var sources: ArrayList<SourceUI> = ArrayList()

    var message: String = ""

    fun resetId() {
        categoryId = R.id.action_all
    }
}
