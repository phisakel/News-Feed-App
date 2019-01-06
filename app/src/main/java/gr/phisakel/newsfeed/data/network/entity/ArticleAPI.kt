package gr.phisakel.newsfeed.data.network.entity

import android.support.annotation.Keep

@Keep
data class ArticleAPI(
        val author: String? = null,
        val title: String? = null,
        val description: String? = null,
        val url: String? = null,
        val urlToImage: String? = null,
        val publishedAt: String? = null
)
