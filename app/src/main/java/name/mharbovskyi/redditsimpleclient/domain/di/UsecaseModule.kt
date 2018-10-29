package name.mharbovskyi.redditsimpleclient.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository
import name.mharbovskyi.redditsimpleclient.domain.usecase.*
import javax.inject.Named

const val CACHED = "cached"
const val REMOTE = "remote"

@Module
class UsecaseModule {

    @Named(REMOTE)
    @Provides
    fun provideRemoteLoadPostsUsecase(
        remoteRepository: LoadPaginatedPostsRepository,
        connectionChecker: ConnectionChecker
    ): LoadPostsUsecase =
        RemoteLoadPostsUsecase(remoteRepository, connectionChecker)

    @Named(CACHED)
    @Provides
    fun provideCachedLoadPostsUsecase(
        localRepository: LocalRepository,
        @Named(REMOTE)
        loadPostsUsecase: LoadPostsUsecase
    ): LoadPostsUsecase =
        CachedLoadPostsUsecase(loadPostsUsecase, localRepository)

    @Provides
    fun providePaginationUsecase(
        @Named(CACHED)
        loadPostsUsecase: LoadPostsUsecase
    ) = PaginationUsecase(loadPostsUsecase)

    @Provides
    fun provideClearPostsUsecase(
        localRepository: LocalRepository,
        connectionChecker: ConnectionChecker
    ) = ClearLocalPostsUsecase(localRepository, connectionChecker)

}