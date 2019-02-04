package gr.phisakel.newsfeed.domain

import gr.phisakel.newsfeed.data.network.entity.ArticleAPI
import gr.phisakel.newsfeed.data.storage.entity.SourceDB
import gr.phisakel.newsfeed.data.network.entity.SourceAPI
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.entity.SourceUI

object Mapper {

    fun articleAPItoUI(item: ArticleAPI): ArticleUI =
            ArticleUI(
                    title = item.title,
                    description = item.description,
                    url = item.url,
                    urlToImage = item.urlToImage
            )

    fun sourceAPItoDB(item: SourceAPI): SourceDB {
        val source = SourceDB()
        with(source) {
            id = item.id ?: ""
            name = item.name ?: ""
            description = item.description ?: ""
            url = item.url ?: ""
            category = item.category ?: ""
            country = item.country ?: ""
            if(country.equals("cy", ignoreCase = true))
            { country = "gr"}
            isEnabled = item.isEnabled ?: false
        }
        return source
    }

    fun sourceDBtoUI(item: SourceDB): SourceUI =
            SourceUI(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    url = item.url,
                    category = item.category,
                    country = if(item.country.equals("cy", ignoreCase = true)) { "gr" } else { item.country},
                    isEnabled = item.isEnabled
            )
}
