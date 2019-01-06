package gr.phisakel.newsfeed.presentation.ui.newssources

import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.presentation.base.BaseModel
import gr.phisakel.newsfeed.presentation.entity.SourceUI

class NewsSourcesModel(subscriber: (BaseModel) -> Unit) : BaseModel(subscriber) {

    var categoryId: Int = R.id.action_all

    var loading: Boolean = false

    var sources: ArrayList<SourceUI> = ArrayList()

    var message: String = ""

    fun resetId() {
        categoryId = R.id.action_all
    }
}
