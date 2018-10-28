package name.mharbovskyi.redditsimpleclient.presentation.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.support.DaggerFragment
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_post_list.*
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.presenter.PostsViewModel
import name.mharbovskyi.redditsimpleclient.presentation.presenter.factory.PostViewModelFactory
import name.mharbovskyi.redditsimpleclient.presentation.view.adapter.PostsAdapter
import javax.inject.Inject

class PostListFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: PostViewModelFactory
    private lateinit var viewModel: PostsViewModel

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = requireActivity().run {
            ViewModelProviders.of(this, viewModelFactory)
                .get(PostsViewModel::class.java)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewManager = LinearLayoutManager(this.context)
        postsAdapter = PostsAdapter()

        posts_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = postsAdapter
        }

        posts_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = viewManager.childCount
                val itemCount = viewManager.itemCount
                val firstVisibleItemPosition = viewManager.findFirstVisibleItemPosition()

                viewModel.scrolled(visibleItemCount, firstVisibleItemPosition, itemCount)
            }
        })

        viewModel.start()

        viewModel.posts.subscribeBy {
            Log.d(TAG, "received ${it.size} posts")
            postsAdapter.addPosts(it)
        }

        viewModel.errors.subscribeBy {
            Toast.makeText(this.context, it.messageId, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        val TAG = PostListFragment::class.java.simpleName
    }
}
