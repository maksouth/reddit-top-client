package name.mharbovskyi.redditsimpleclient.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import name.mharbovskyi.redditsimpleclient.presentation.di.scope.FragmentScope
import name.mharbovskyi.redditsimpleclient.presentation.view.PostListFragment

@Module
interface FragmentsProviderModule {
    @FragmentScope
    @ContributesAndroidInjector
    fun contributesPostListFragment(): PostListFragment
}