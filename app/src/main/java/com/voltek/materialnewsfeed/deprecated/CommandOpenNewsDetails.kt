package com.voltek.materialnewsfeed.deprecated

import com.voltek.materialnewsfeed.data.networking.Article
import com.voltek.materialnewsfeed.navigation.proxy.Command

@Deprecated("Use CommandStartActivity with Bundle parameters instead")
class CommandOpenNewsDetails(val article: Article) : Command()
