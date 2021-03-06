package name.mharbovskyi.redditsimpleclient.presentation.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_post_list.*
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.viewmodel.PostsViewModel
import name.mharbovskyi.redditsimpleclient.presentation.viewmodel.factory.PostViewModelFactory
import name.mharbovskyi.redditsimpleclient.presentation.view.adapter.PostsAdapter
import javax.inject.Inject

class PostListFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: PostViewModelFactory
    private lateinit var viewModel: PostsViewModel

    private var fullImageListener: ShowFullImageListener? = null

    private lateinit var postsAdapter: PostsAdapter

    val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fullImageListener = activity as? ShowFullImageListener
    }

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

        viewModel.start()

        setListeners()
    }

    private fun setListeners() {

        posts_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                    viewModel.scrolled(it.childCount, it.findFirstVisibleItemPosition(), it.itemCount)
                }
            }
        })

        viewModel.posts.subscribe {
            Log.d(TAG, "received ${it.size} posts")
            postsAdapter.addPosts(it)
        }.addTo(compositeDisposable)

        viewModel.errors.subscribe { viewError ->
            context?.let {
                Toast.makeText(this.context, viewError.messageId, Toast.LENGTH_LONG).show()
            }
        }.addTo(compositeDisposable)

        viewModel.infos.subscribe { viewInfo ->
            context?.let {
                Toast.makeText(it, viewInfo.messageId, Toast.LENGTH_SHORT).show()
            }
        }.addTo(compositeDisposable)

        viewModel.showImageData.subscribe { showFullImage(it) }.addTo(compositeDisposable)

        postsAdapter.thumbnailClicks.subscribe { viewModel.thumbnailClick(it) }.addTo(compositeDisposable)

    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }

    private fun showFullImage(contentUrl: String) =
        fullImageListener?.showImage(contentUrl)


    companion object {
        val TAG = PostListFragment::class.java.simpleName
    }
}
