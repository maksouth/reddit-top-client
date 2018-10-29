package name.mharbovskyi.redditsimpleclient.domain.usecase

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository
import java.lang.Exception


interface LoadPostsUsecase {
    fun loadMore(): Single<List<Post>>
}

class NoConnectionException: Exception()
const val PAGE_SIZE = 10

/**
 * check connection
 * if not connected - loads posts from db
 * else loads posts from api
 * and saves loaded posts to db
 *
 */
class RemoteLoadPostsUsecase(
    private val remoteRepository: LoadPaginatedPostsRepository,
    private val connectionChecker: ConnectionChecker
): LoadPostsUsecase {



    override fun loadMore(): Single<List<Post>> =
        connectionChecker.isConnected()
            .flatMap { isConnected ->
                if (isConnected)
                    remoteRepository.loadNext(PAGE_SIZE)
                else Single.error<List<Post>>(NoConnectionException())
            }
}

class CachedLoadPostsUsecase(
    private val remoteLoadPostsUsecase: LoadPostsUsecase,
    private val localRepository: LocalRepository
): LoadPostsUsecase {

    override fun loadMore(): Single<List<Post>> =
        remoteLoadPostsUsecase.loadMore()
            .doOnSuccess { localRepository.save(it) }
            .onErrorResumeNext(localRepository.loadNext(PAGE_SIZE))
}