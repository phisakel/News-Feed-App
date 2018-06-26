package com.voltek.newsfeed.presentation.ui.details

import com.voltek.newsfeed.presentation.base.BaseModelOld

class DetailsModel(subscriber: (BaseModelOld) -> Unit) : BaseModelOld(subscriber) {

    var articleLoaded = false

    var title: String = ""
    var description: String = ""
    var urlToImage: String = ""
    var source: String = ""
}
