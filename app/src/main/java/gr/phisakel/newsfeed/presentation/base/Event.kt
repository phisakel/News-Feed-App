package gr.phisakel.newsfeed.presentation.base

import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.entity.SourceUI

/**
 * UI input events
 */
sealed class Event {

    class OpenWebsite(val url: String = "") : Event()

    class OpenNewsSources : Event()

    class OpenArticleDetails(val article: ArticleUI) : Event()

    class EnableNewsSource(val source: SourceUI): Event()

    class Refresh : Event()

    class Share : Event()

    class Back : Event()

    class FilterSources(val filter: String, val id: Int) : Event()
}
