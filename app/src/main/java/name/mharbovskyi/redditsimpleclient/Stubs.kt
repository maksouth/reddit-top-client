package name.mharbovskyi.redditsimpleclient

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.SavePostsRepository
import javax.inject.Inject

class StubLocalDataSource: LoadPaginatedPostsRepository {
    override fun loadNext(size: Int)
            : Single<List<Post>> =
            Single.never()
}

class StubConnectionChecker @Inject constructor(): ConnectionChecker {
    override fun isConnected(): Single<Boolean> =
            Single.just(false)
}

class StubSavePostsRepository @Inject constructor(): SavePostsRepository {
    override fun save(posts: List<Post>) {}
}

@Module
interface StubsModule {
    @Binds fun bindConnectionChecker(
        stubConnectionChecker: StubConnectionChecker
    ): ConnectionChecker

//    @Binds fun bindSavePostRepository(
//        stubSavePostsRepository: StubSavePostsRepository
//    ): SavePostsRepository
}