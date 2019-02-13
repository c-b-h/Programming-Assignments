package se.ingenuity.tattoodo.feature.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import se.ingenuity.tattoodo.R
import se.ingenuity.tattoodo.databinding.ViewPostItemBinding
import se.ingenuity.tattoodo.model.Image
import se.ingenuity.tattoodo.model.Post
import se.ingenuity.tattoodo.model.Posts
import se.ingenuity.tattoodo.model.Uploader
import se.ingenuity.tattoodo.vo.Resource
import se.ingenuity.tattoodo.vo.Status
import se.ingenuity.tattoodo.widget.ViewTypeDividerDecoration

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val loadingPost =
        Post(
            0,
            Image(null),
            null,
            Uploader(null)
        )
    private val loadingPosts =
        Resource.loading(Posts(List(5) { loadingPost }))

    private val divider = ViewTypeDividerDecoration
        .Builder()
        .addAttributeResource(
            android.R.attr.listDivider,
            R.dimen.base_margin_2x,
            ViewTypeDividerDecoration.Placement.BOTTOM,
            getItemViewType(0)
        )
        .build()

    var onPostClickListener: (Long, Array<String>) -> Unit = { _, _ -> }

    var posts: Resource<Posts> = loadingPosts
        set(value) {
            field = if (value.status != Status.SUCCESS) {
                loadingPosts
            } else {
                value
            }

            notifyItemRangeChanged(0, field.data!!.data.size, Any())
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, onPostClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts.data!!.data[position], posts.status)
    }

    override fun getItemCount() = posts.data!!.data.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(divider)

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        recyclerView.removeItemDecoration(divider)
    }


    class ViewHolder(
        parent: ViewGroup,
        private val onPostClickListener: (Long, Array<String>) -> Unit
    ) : RecyclerView.ViewHolder(
        ViewPostItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).root
    ) {
        private val binding: ViewPostItemBinding = DataBindingUtil.getBinding(itemView)!!

        fun bind(post: Post, status: Status) {
            with(binding) {

                this.post = post
                this.status = status

                executePendingBindings()

                val tags = mutableListOf<String>()
                image.transitionName?.let {
                    image.tag = it
                    tags.add(it)
                }

                name.transitionName?.let {
                    name.tag = it
                    tags.add(it)
                }

                uploaderImage.transitionName?.let {
                    uploaderImage.tag = it
                    tags.add(it)
                }

                this.root.setOnClickListener { onPostClickListener(post.id, tags.toTypedArray()) }
            }
        }
    }
}