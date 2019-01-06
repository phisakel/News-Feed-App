package gr.phisakel.newsfeed.presentation.ui.list

import android.content.Intent
import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.presentation.base.BaseActivity
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.navigation.command.*
import gr.phisakel.newsfeed.presentation.navigation.proxy.Command
import gr.phisakel.newsfeed.presentation.ui.details.DetailsFragment
import gr.phisakel.newsfeed.presentation.ui.newssources.NewsSourcesActivity
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity() {

    private var mDualPane = false

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()
        title = getString(gr.phisakel.newsfeed.R.string.title_list)

        mDualPane = details_fragment_container != null

        if (savedInstanceState == null) {
            replaceFragment(
                    ListFragment.newInstance(),
                    R.id.list_fragment_container,
                    ListFragment.TAG)
        } else if (!mDualPane && supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        title = getString(R.string.title_list)
    }

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandOpenNewsSourcesScreen -> openNewsSources()
        is CommandOpenArticleDetailsScreen -> openDetails(command.article)
        is CommandShareArticle -> shareArticle(command)
        is CommandOpenWebsite -> openWebsite(command)
        is CommandBack -> goBack()
        else -> false
    }

    private fun openDetails(article: ArticleUI): Boolean {
        if (mDualPane) {
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.details_fragment_container,
                    DetailsFragment.TAG,
                    true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            replaceFragment(
                    DetailsFragment.newInstance(article),
                    R.id.list_fragment_container,
                    DetailsFragment.TAG,
                    true)
        }
        return true
    }

    private fun openNewsSources(): Boolean {
        val intent = Intent(this, NewsSourcesActivity::class.java)
        startActivity(intent)
        return true
    }
}
