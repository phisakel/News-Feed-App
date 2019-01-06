package gr.phisakel.newsfeed

import gr.phisakel.newsfeed.data.network.entity.ArticleAPI
import gr.phisakel.newsfeed.data.network.entity.SourceAPI
import gr.phisakel.newsfeed.data.storage.entity.SourceDB
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.entity.SourceUI

object MockData {

    const val stringNoConnection = "stringNoConnection"
    const val stringServerError = "stringServerError"

    fun sourceAPI() = SourceAPI(
            id = "id",
            name = "name",
            description = "description",
            url = "url",
            category = "category",
            country = "country",
            isEnabled = false)

    fun articleAPI() = ArticleAPI(
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "urlToImage")

    fun sourceDB(): SourceDB {
        val s = SourceDB()
        with(s) {
            id = "id"
            name = "name"
            description = "description"
            url = "url"
            category = "category"
            country = "country"
            isEnabled = false
        }
        return s
    }

    fun sourceUI() = SourceUI(
            "id",
            "name",
            "description",
            "url",
            "category",
            "country",
            false)

    fun articleUI() = ArticleUI(
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "urlToImage")
}
