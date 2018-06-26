package com.voltek.newsfeed.presentation.ui.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.newsfeed.presentation.base.BaseViewOld

interface DetailsView : BaseViewOld {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: DetailsModel)
}
