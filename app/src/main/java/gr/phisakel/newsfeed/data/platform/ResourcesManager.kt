package gr.phisakel.newsfeed.data.platform

import android.content.Context
import android.content.res.Configuration
import android.support.annotation.StringRes
import android.os.Build
import java.util.*

class ResourcesManager(private val context: Context) {
    val enLoc = Locale("en")
    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {

            context.resources.configuration.locale
        }
    }

    fun getLocaleStringResource(requestedLocale: Locale, resourceId: Int, context: Context): String {
        val result: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // use latest api
            val config = Configuration(context.resources.configuration)
            config.setLocale(requestedLocale)
            result = context.createConfigurationContext(config).getText(resourceId).toString()
        } else { // support older android versions
            val resources = context.resources
            val conf = resources.configuration
            val savedLocale = conf.locale
            conf.locale = requestedLocale
            resources.updateConfiguration(conf, null)
            // retrieve resources from desired locale
            result = resources.getString(resourceId)
            // restore original locale
            conf.locale = savedLocale
            resources.updateConfiguration(conf, null)
        }
        return result
    }

    fun getString(@StringRes id: Int): String = context.getString(id)
    fun getStringEn(@StringRes id: Int): String = getLocaleStringResource(enLoc, id, context)
    val currentCountryIsoCode = getCurrentLocale(context).country
    val currentCountryDisplay = getCurrentLocale(context).displayCountry
    val currentLanguageCode = getCurrentLocale(context).language
}
