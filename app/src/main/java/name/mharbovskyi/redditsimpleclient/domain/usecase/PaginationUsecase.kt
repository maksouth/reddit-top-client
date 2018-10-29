package name.mharbovskyi.redditsimpleclient.domain.usecase

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.model.Post

sealed class PaginationState
class NextPage(val posts: List<Post>): PaginationState()
object LoadedAll : PaginationState()

const val TOTAL_PAGES = 5

class PaginationUsecase(private val loadPostsUsecase: LoadPostsUsecase) {

    private var pagesLoaded: Int = 0

    fun loadMore(): Single<PaginationState> {
        if (pagesLoaded >= TOTAL_PAGES)
            return Single.just(LoadedAll)

        return loadPostsUsecase.loadMore()
            .doOnSuccess { pagesLoaded++ }
            .map { NextPage(it) }
    }

}