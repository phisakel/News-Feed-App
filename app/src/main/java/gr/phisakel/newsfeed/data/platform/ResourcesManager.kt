package gr.phisakel.newsfeed.data.platform

import android.content.Context
import android.support.annotation.StringRes
import android.os.Build
import java.util.*


class ResourcesManager(private val context: Context) {
    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {

            context.resources.configuration.locale
        }
    }
    fun getString(@StringRes id: Int): String = context.getString(id)
    val currentCountryIsoCode = getCurrentLocale(context).country
    val currentCountryDisplay = getCurrentLocale(context).displayCountry
    val currentLanguageCode = getCurrentLocale(context).language
}
