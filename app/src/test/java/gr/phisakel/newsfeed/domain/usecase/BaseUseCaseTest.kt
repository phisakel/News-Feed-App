package gr.phisakel.newsfeed.domain.usecase

import gr.phisakel.newsfeed.CurrentThreadExecutor
import io.reactivex.schedulers.Schedulers

abstract class BaseUseCaseTest {

    protected val scheduler = Schedulers.from(CurrentThreadExecutor())
}
