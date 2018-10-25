package name.mharbovskyi.redditsimpleclient.presentation.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.presenter.PostsViewModel
import name.mharbovskyi.redditsimpleclient.presentation.presenter.factory.PostViewModelFactory
import javax.inject.Inject

class PostListFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: PostViewModelFactory

    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
