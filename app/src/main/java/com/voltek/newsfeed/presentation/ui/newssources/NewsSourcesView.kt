package com.voltek.newsfeed.presentation.ui.newssources

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.newsfeed.presentation.base.BaseViewOld

interface NewsSourcesView : BaseViewOld {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: NewsSourcesModel)
}
