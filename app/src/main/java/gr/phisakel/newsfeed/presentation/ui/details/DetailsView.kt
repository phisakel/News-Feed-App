package gr.phisakel.newsfeed.presentation.ui.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import gr.phisakel.newsfeed.presentation.base.BaseView

interface DetailsView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: DetailsModel)
}
