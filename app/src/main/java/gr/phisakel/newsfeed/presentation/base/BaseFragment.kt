package gr.phisakel.newsfeed.presentation.base

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import gr.phisakel.newsfeed.utils.SubscriptionsHolder
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : MvpAppCompatFragment(),
        SubscriptionsHolder {

    // Holds all disposables with input events subscriptions
    override val mDisposable = CompositeDisposable()

    // Helper methods
    protected fun toast(
            message: String,
            length: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, message, length).show()
    }
}
