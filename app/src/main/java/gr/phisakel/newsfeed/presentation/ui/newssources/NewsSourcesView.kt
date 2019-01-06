package gr.phisakel.newsfeed.presentation.ui.newssources

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import gr.phisakel.newsfeed.presentation.base.BaseView

interface NewsSourcesView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(model: NewsSourcesModel)
}
