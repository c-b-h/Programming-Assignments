package se.ingenuity.peakon.di.databinding

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import se.ingenuity.peakon.databinding.ImageViewBindingAdapter

@Module
class BindingModule {
    @BindingScope
    @Provides
    fun provideImageViewBindingAdapter(picasso: Picasso): ImageViewBindingAdapter {
        return ImageViewBindingAdapter(picasso)
    }
}