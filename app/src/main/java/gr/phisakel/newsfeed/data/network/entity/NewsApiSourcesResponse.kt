package gr.phisakel.newsfeed.data.network.entity

import android.support.annotation.Keep

@Keep
data class NewsApiSourcesResponse(val sources: List<SourceAPI>)
