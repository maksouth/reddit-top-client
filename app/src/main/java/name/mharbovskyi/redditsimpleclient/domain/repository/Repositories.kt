package name.mharbovskyi.redditsimpleclient.domain.repository

import io.reactivex.Single
import name.mharbovskyi.redditsimpleclient.domain.model.Post

interface LocalRepository: LoadPaginatedPostsRepository  {
    fun save(posts: List<Post>)
    fun clear()
}

interface LoadPaginatedPostsRepository {
    fun loadNext(size: Int = 10): Single<List<Post>>
}