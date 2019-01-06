package gr.phisakel.newsfeed.domain.usecase

/**
 * Generic class for interactor execution results.
 */
data class Result<out Type : Any?>(
        val data: Type? = null,
        val message: String = ""
)
