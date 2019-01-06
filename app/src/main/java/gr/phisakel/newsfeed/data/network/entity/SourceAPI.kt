package gr.phisakel.newsfeed.data.network.entity

import android.support.annotation.Keep

@Keep
data class SourceAPI(
        val id: String? = null,
        val name: String? = null,
        val description: String? = null,
        val url: String? = null,
        val category: String? = null,
        val language: String? = null,
        val country: String? = null,
        val isEnabled: Boolean? = null
)
