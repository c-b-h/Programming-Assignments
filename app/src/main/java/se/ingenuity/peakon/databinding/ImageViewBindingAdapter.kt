package se.ingenuity.peakon.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class ImageViewBindingAdapter constructor(private val picasso: Picasso) {
    private val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(Shimmer.AlphaHighlightBuilder().build())
    }

    @BindingAdapter(value = ["imageUrl"])
    fun loadImage(view: ImageView, url: String?) {
        if (url != null) {
            picasso
                .load(url)
                .noFade()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(shimmerDrawable)
                .into(view)
        } else {
            view.setImageDrawable(shimmerDrawable)
        }
    }
}