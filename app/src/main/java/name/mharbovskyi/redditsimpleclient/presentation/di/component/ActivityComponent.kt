package name.mharbovskyi.redditsimpleclient.presentation.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import name.mharbovskyi.redditsimpleclient.StubsModule
import name.mharbovskyi.redditsimpleclient.data.di.DataSourceModule
import name.mharbovskyi.redditsimpleclient.domain.di.UsecaseModule
import name.mharbovskyi.redditsimpleclient.presentation.di.module.FragmentsProviderModule
import name.mharbovskyi.redditsimpleclient.presentation.di.scope.ActivityScope
import name.mharbovskyi.redditsimpleclient.presentation.view.MainActivity
import javax.inject.Singleton

@Component(modules = [
    AndroidInjectionModule::class,
    UsecaseModule::class,
    DataSourceModule::class,
    FragmentsProviderModule::class,
    StubsModule::class
])
interface ActivityComponent {

    fun inject(activity: MainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): ActivityComponent
    }
}