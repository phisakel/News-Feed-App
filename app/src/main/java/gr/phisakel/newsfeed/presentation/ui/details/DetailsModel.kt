package gr.phisakel.newsfeed.presentation.ui.details

import gr.phisakel.newsfeed.presentation.base.BaseModel

class DetailsModel(subscriber: (BaseModel) -> Unit) : BaseModel(subscriber) {

    var articleLoaded = false

    var title: String = ""
    var description: String = ""
    var urlToImage: String = ""
    var source: String = ""
}
