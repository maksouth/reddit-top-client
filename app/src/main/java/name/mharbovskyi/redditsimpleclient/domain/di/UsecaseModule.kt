package name.mharbovskyi.redditsimpleclient.domain.di

import dagger.Module
import dagger.Provides
import name.mharbovskyi.redditsimpleclient.data.di.DataSourceModule
import name.mharbovskyi.redditsimpleclient.domain.ConnectionChecker
import name.mharbovskyi.redditsimpleclient.domain.repository.LoadPaginatedPostsRepository
import name.mharbovskyi.redditsimpleclient.domain.repository.LocalRepository
import name.mharbovskyi.redditsimpleclient.domain.usecase.ClearLocalPostsUsecase
import name.mharbovskyi.redditsimpleclient.domain.usecase.LoadPostsUsecase
import javax.inject.Named

@Module
class UsecaseModule {

    @Provides
    fun provideLoadPostsUsecase(
        @Named(DataSourceModule.REMOTE)
        remoteRepository: LoadPaginatedPostsRepository,
        localRepository: LocalRepository,
        connectionChecker: ConnectionChecker
    ) = LoadPostsUsecase(remoteRepository, localRepository, connectionChecker)

    @Provides
    fun provideClearPostsUsecase(
        localRepository: LocalRepository,
        connectionChecker: ConnectionChecker
    ) = ClearLocalPostsUsecase(localRepository, connectionChecker)

}