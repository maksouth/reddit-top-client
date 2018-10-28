package name.mharbovskyi.redditsimpleclient.presentation.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.di.component.DaggerActivityComponent
import javax.inject.Inject

class MainActivity
    : AppCompatActivity(), HasSupportFragmentInjector
{

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerActivityComponent
            .builder()
            .applicationContext(applicationContext)
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostListFragment())
            .commit()
    }

    override fun supportFragmentInjector() =
            fragmentDispatchingAndroidInjector

}
