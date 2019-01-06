package gr.phisakel.newsfeed.presentation.ui.splash

import android.content.Intent
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import gr.phisakel.newsfeed.App
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenArticlesListScreen
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import gr.phisakel.newsfeed.presentation.navigation.proxy.Command
import gr.phisakel.newsfeed.presentation.base.BaseActivity
import gr.phisakel.newsfeed.presentation.ui.list.ListActivity
import gr.phisakel.newsfeed.presentation.ui.newssources.NewsSourcesActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(),
        SplashView {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun executeCommand(command: Command): Boolean = when (command) {
        is CommandOpenArticlesListScreen -> {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        is CommandOpenNewsSourcesScreen -> {
            val intent = Intent(this, NewsSourcesActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        else -> false
    }

    override fun attachInputListeners() {}

    override fun detachInputListeners() {}
}
