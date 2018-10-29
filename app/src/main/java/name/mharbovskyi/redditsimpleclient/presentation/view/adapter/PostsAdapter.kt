package name.mharbovskyi.redditsimpleclient.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.reactivex.subjects.PublishSubject
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewPost

class PostsAdapter: RecyclerView.Adapter<ViewHolder>() {

    val thumbnailClicks: PublishSubject<String> = PublishSubject.create()
    private val posts = mutableListOf<ViewPost>()

    fun addPosts(newPosts: List<ViewPost>) {
        posts.addAll(newPosts)
        notifyItemRangeInserted(posts.size - newPosts.size, newPosts.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populate(posts[position])

        holder.thumbnailImage.setOnClickListener { _ ->
            holder.contentUrl?.let {
                thumbnailClicks.onNext(it)
            }
        }
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var contentUrl: String? = null
        private set
    val thumbnailImage = view.findViewById<ImageView>(R.id.thumbnail)

    private val authorLabel = view.findViewById<TextView>(R.id.author_label)
    private val titleLabel = view.findViewById<TextView>(R.id.title_label)
    private val commentsLabel = view.findViewById<TextView>(R.id.comments_label)
    private val publishedLabel = view.findViewById<TextView>(R.id.published_label)
    private val hasFullImageLabel = view.findViewById<TextView>(R.id.has_full_image_label)

    fun populate(post: ViewPost) {
        if (!post.showThumbnail)
            thumbnailImage.visibility = View.GONE
        else {
            thumbnailImage.visibility = View.VISIBLE
            Glide.with(thumbnailImage)
                .load(post.thumbnailUrl)
                .into(thumbnailImage)
        }

        if (post.showContent) {
            contentUrl = post.contentUrl
            hasFullImageLabel.setText(R.string.has_full_image)
        } else {
            hasFullImageLabel.setText(R.string.not_has_full_image)
        }

        authorLabel.text = post.authorName
        titleLabel.text = post.title
        commentsLabel.text = post.commentsCount
        publishedLabel.text = post.createdBefore
    }
}