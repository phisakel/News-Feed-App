package gr.phisakel.newsfeed.presentation.ui.details

import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import gr.phisakel.newsfeed.App
import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.presentation.base.BaseFragment
import gr.phisakel.newsfeed.presentation.base.Event
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class DetailsFragment : BaseFragment(),
        DetailsView {

    companion object {
        const val TAG = "DetailsFragment"

        const val ARG_ARTICLE = "ARG_ARTICLE"

        fun newInstance(article: ArticleUI): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_ARTICLE, article)
            fragment.arguments = args
            return fragment
        }
    }

    init {
        App.appComponent.inject(this)
        setHasOptionsMenu(true)
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_details, menu)
    }

    override fun attachInputListeners() {
        val article = arguments!!.getParcelable(ARG_ARTICLE) as ArticleUI
        presenter.setArticle(article)

        RxToolbar.itemClicks(activity!!.toolbar)
                .subscribe {
                    when (it.itemId) {
                        R.id.action_share -> presenter.event(Event.Share())
                        R.id.action_website -> presenter.event(Event.OpenWebsite())
                        R.id.action_news_sources -> presenter.event(Event.OpenNewsSources())
                    }
                }
                .bind()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            presenter.event(Event.Back())
            true
        }
        else -> false
    }

    override fun detachInputListeners() {
        resetCompositeDisposable()
    }

    override fun render(model: DetailsModel) {
        if (model.articleLoaded) {
            activity!!.title = model.source

            Glide
                    .with(context)
                    .load(model.urlToImage)
                    .into(im_image_details)

            tv_title_details.text = model.title
            tv_description.text = model.description

            tv_message.text = ""
        } else {
            im_image_details.setImageResource(android.R.color.transparent)
            tv_title_details.text = ""
            tv_description.text = ""

            tv_message.text = getString(R.string.error_empty_details)
        }
    }
}
