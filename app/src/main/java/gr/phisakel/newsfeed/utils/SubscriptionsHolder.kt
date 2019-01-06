package gr.phisakel.newsfeed.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface SubscriptionsHolder {

    /**
     * Holds Disposables
     */
    val mDisposable: CompositeDisposable

    /**
     * Easy add disposables to composite with this function
     */
    fun Disposable.bind() = mDisposable.add(this)

    /**
     * Unsubscribe when subscriptions no longer needed
     */
    fun resetCompositeDisposable() {
        mDisposable.clear()
    }
}
