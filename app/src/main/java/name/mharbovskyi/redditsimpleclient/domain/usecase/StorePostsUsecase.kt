package name.mharbovskyi.redditsimpleclient.domain.usecase

import name.mharbovskyi.redditsimpleclient.domain.model.Post
import name.mharbovskyi.redditsimpleclient.domain.repository.SavePostsRepository

class StorePostsUsecase(private val savePostsRepository: SavePostsRepository) {
    fun store(posts: List<Post>) {
        TODO()
    }
}