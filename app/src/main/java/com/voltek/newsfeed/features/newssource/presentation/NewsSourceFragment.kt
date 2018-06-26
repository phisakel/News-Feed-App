package com.voltek.newsfeed.features.newssource.presentation

import android.os.Bundle
import android.view.View
import com.voltek.newsfeed.App
import com.voltek.newsfeed.R
import com.voltek.newsfeed.base.presentation.adapter.BaseRecyclerAdapter
import com.voltek.newsfeed.base.presentation.mvi.MviFragment
import com.voltek.newsfeed.base.presentation.mvi.MviPresenter
import javax.inject.Inject

class NewsSourceFragment : MviFragment<NewsSourceState>() {

    override fun inject() = App.component.inject(this)
    override fun getLayout() = R.layout.fragment_list

    @Inject
    lateinit var presenter: NewsSourcePresenter
    override fun getPresenter(): MviPresenter<NewsSourceState> = presenter

    lateinit var adapter: BaseRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = BaseRecyclerAdapter()
    }

    override fun render(state: NewsSourceState) {}

    companion object {
        private const val ARG_IS_IN_ENTRY_POINT_MODE = "arg_is_in_entry_point_mode"

        fun newInstance(args: Any? = null) = NewsSourceFragment().apply {
            val isInEntryPointMode = args as? Boolean ?: false
            arguments = Bundle().apply {
                putBoolean(ARG_IS_IN_ENTRY_POINT_MODE, isInEntryPointMode)
            }
        }
    }
}
