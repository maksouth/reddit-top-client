package name.mharbovskyi.redditsimpleclient.domain.usecase

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository

const val PAGE_SIZE = 10

/**
 * check connection
 * if not connected - loads posts from db
 * else loads posts from api
 * and saves loaded posts to db
 *
 */
class LoadPostsUsecase(private val remoteRepository: LoadPaginatedPostsRepository,
                       private val localRepository: LocalRepository,
                       private val connectionChecker: ConnectionChecker) {
    fun loadMore(): Single<List<Post>> {
        return connectionChecker.isConnected()
                .flatMap { isConnected ->
                    if (isConnected)
                        remoteRepository.loadNext(PAGE_SIZE)
                            .onErrorResumeNext(localRepository.loadNext(PAGE_SIZE))
                            .doOnSuccess {
                                localRepository.save(it)
                            }
                    else localRepository.loadNext(PAGE_SIZE)
                }
    }
}