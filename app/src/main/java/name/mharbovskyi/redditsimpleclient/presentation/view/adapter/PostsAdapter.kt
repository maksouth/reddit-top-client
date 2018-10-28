package name.mharbovskyi.redditsimpleclient.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import name.mharbovskyi.redditsimpleclient.R
import name.mharbovskyi.redditsimpleclient.presentation.model.ViewPost

class PostsAdapter: RecyclerView.Adapter<ViewHolder>() {

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
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val authorLabel = view.findViewById<TextView>(R.id.author_label)
    private val titleLabel = view.findViewById<TextView>(R.id.title_label)
    private val commentsLabel = view.findViewById<TextView>(R.id.comments_label)
    private val publishedLabel = view.findViewById<TextView>(R.id.published_label)
    private val thumbnailImage = view.findViewById<ImageView>(R.id.thumbnail)
    private var contentUrl: String? = null

    fun populate(post: ViewPost) {
        if (!post.hasThumbnail)
            thumbnailImage.visibility = View.GONE
        else
            Glide.with(thumbnailImage)
                .load(post.thumbnailUrl)
                .into(thumbnailImage)

        if (post.hasContentUrl)
            contentUrl = post.contentUrl

        authorLabel.text = post.authorName
        titleLabel.text = post.title
        commentsLabel.text = post.commentsCount
        publishedLabel.text = post.createdBefore
    }
}