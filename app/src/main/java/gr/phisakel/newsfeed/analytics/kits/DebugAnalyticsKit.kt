package gr.phisakel.newsfeed.analytics.kits

import gr.phisakel.newsfeed.Logger
import gr.phisakel.newsfeed.analytics.AnalyticsKit

class DebugAnalyticsKit : AnalyticsKit {

    override fun sendEvent(name: String, params: Map<String, Any>) {
        Logger.d("$name $params")
    }
}
