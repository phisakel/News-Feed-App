package gr.phisakel.newsfeed.presentation.ui.list

import gr.phisakel.newsfeed.presentation.base.BaseModel
import gr.phisakel.newsfeed.presentation.entity.ArticleUI

class ListModel(subscriber: (BaseModel) -> Unit) : BaseModel(subscriber) {

    var scrollToTop: Boolean = false

    var loading: Boolean = false

    var articles: ArrayList<ArticleUI> = ArrayList()

    var message: String = ""

    fun addData(data: List<ArticleUI>?) {
        if (data != null && !data.isEmpty())
            articles.addAll(data)
    }
}
