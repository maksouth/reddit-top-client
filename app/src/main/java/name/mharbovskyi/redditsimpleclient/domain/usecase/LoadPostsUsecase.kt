package name.mharbovskyi.redditsimpleclient.domain.usecase

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository

const val PAGE_SIZE = 10

class LoadPostsUsecase(private val remoteRepository: LoadPaginatedPostsRepository,
                       private val localRepository: LoadPaginatedPostsRepository,
                       private val connectionChecker: ConnectionChecker) {
    fun loadMore(): Single<List<Post>> {
        return connectionChecker.isConnected()
                .flatMap { isConnected ->
                    if (isConnected)
                        remoteRepository.loadNext(PAGE_SIZE)
                                .onErrorResumeNext(localRepository.loadNext(PAGE_SIZE))
                    else localRepository.loadNext(PAGE_SIZE)
                }
    }
}