package name.mharbovskyi.redditsimpleclient.presentation.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import name.mharbovskyi.redditsimpleclient.data.di.DataSourceModule
import name.mharbovskyi.redditsimpleclient.device.DeviceModule
import name.mharbovskyi.redditsimpleclient.domain.di.UsecaseModule
import name.mharbovskyi.redditsimpleclient.presentation.di.module.AndroidModule
import name.mharbovskyi.redditsimpleclient.presentation.di.module.FragmentsProviderModule
import name.mharbovskyi.redditsimpleclient.presentation.di.scope.ActivityScope
import name.mharbovskyi.redditsimpleclient.presentation.view.MainActivity

@ActivityScope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    UsecaseModule::class,
    DataSourceModule::class,
    FragmentsProviderModule::class,
    DeviceModule::class,
    AndroidModule::class
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