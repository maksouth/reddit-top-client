package name.mharbovskyi.redditsimpleclient.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.data.di.DataSourceModule
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.SavePostsRepository
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadPostsUsecase
import name.mharbovskyi.redditsimpleclient.domain.usecase.StorePostsUsecase
import name.mharbovskyi.redditsimpleclient.presentation.di.scope.ActivityScope
import javax.inject.Named
import javax.inject.Singleton

@Module
class UsecaseModule {

    @Provides
    fun provideLoadPostsUsecase(
        @Named(DataSourceModule.REMOTE)
        remoteRepository: LoadPaginatedPostsRepository,
        @Named(DataSourceModule.LOCAL)
        localRepository: LoadPaginatedPostsRepository,
        connectionChecker: ConnectionChecker
    ) = LoadPostsUsecase(remoteRepository, localRepository, connectionChecker)

    @Provides
    fun provideStorePostsUsecase(
            savePostsRepository: SavePostsRepository
    ) = StorePostsUsecase(savePostsRepository)
}