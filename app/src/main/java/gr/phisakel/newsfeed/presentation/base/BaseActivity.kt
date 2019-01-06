package gr.phisakel.newsfeed.presentation.base

import android.content.Intent
import android.net.Uri
import android.support.annotation.IdRes
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import gr.phisakel.newsfeed.utils.SubscriptionsHolder
import gr.phisakel.newsfeed.App
import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenWebsite
import gr.phisakel.newsfeed.presentation.navigation.proxy.Navigator
import io.reactivex.disposables.CompositeDisposable
import android.widget.Toast
import gr.phisakel.newsfeed.presentation.navigation.command.CommandShareArticle

abstract class BaseActivity : MvpAppCompatActivity(),
        Navigator,
        SubscriptionsHolder {

    // Holds all disposables with input events subscriptions
    override val mDisposable = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        App.getNavigatorBinder().setNavigator(this)
    }

    override fun onPause() {
        super.onPause()
        App.getNavigatorBinder().removeNavigator()
    }

    // Base navigator commands
    protected fun goBack(): Boolean {
        onBackPressed()
        return true
    }

    protected fun shareArticle(command: CommandShareArticle): Boolean {
        if (!command.url.isEmpty() && !command.title.isEmpty()) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            with(shareIntent) {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, command.url)
            }
            startActivity(Intent.createChooser(shareIntent, command.title))
        }
        return true
    }

    protected fun openWebsite(command: CommandOpenWebsite): Boolean {
        if (!command.url.isEmpty()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(command.url))
            if (browserIntent.resolveActivity(packageManager) != null)
                startActivity(browserIntent)
            else
                Toast.makeText(this, getString(R.string.error_no_browser), Toast.LENGTH_LONG).show()
        }
        return true
    }

    // Activity helper methods
    protected fun setupToolbar(
            displayHomeAsUpEnabled: Boolean = false,
            displayShowHomeEnabled: Boolean = false
    ) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        supportActionBar?.setDisplayShowHomeEnabled(displayShowHomeEnabled)
    }

    protected fun replaceFragment(
            fragment: BaseFragment,
            @IdRes id: Int,
            tag: String,
            addToBackStack: Boolean = false
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.replace(id, fragment, tag).commit()
    }
}
