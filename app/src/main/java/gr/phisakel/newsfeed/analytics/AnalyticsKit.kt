package gr.phisakel.newsfeed.analytics

interface AnalyticsKit {

    fun sendEvent(name: String, params: Map<String, Any>)
}
