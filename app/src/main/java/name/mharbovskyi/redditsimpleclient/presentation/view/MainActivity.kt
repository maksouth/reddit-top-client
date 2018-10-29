package name.mharbovskyi.redditsimpleclient.presentation.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.di.component.DaggerActivityComponent
import name.mharbovskyi.redditsimpleclient.presentation.model.ImageVisibilityState
import name.mharbovskyi.redditsimpleclient.presentation.presenter.MainViewModel
import javax.inject.Inject

class MainActivity
    : AppCompatActivity(), HasSupportFragmentInjector, ShowFullImageListener
{
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerActivityComponent
            .builder()
            .applicationContext(applicationContext)
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.fullImageSubject.subscribe {
            if (it.visibilityState == ImageVisibilityState.SHOW)
                showFullScreenImage(it.contentUrl!!)
            else hideFullScreenImage()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostListFragment())
            .commit()
    }

    override fun showImage(contentUrl: String) {
        viewModel.showImage(contentUrl)
    }

    override fun onBackPressed() {
        if (viewModel.shouldExit())
            super.onBackPressed()
    }

    override fun supportFragmentInjector() =
        fragmentDispatchingAndroidInjector

    private fun showFullScreenImage(contentUrl: String) {
        Glide.with(applicationContext)
            .load(contentUrl)
            .into(fullscreen_image)
        fullscreen_image.visibility = View.VISIBLE
    }

    private fun hideFullScreenImage() {
        fullscreen_image.visibility = View.GONE
    }
}
