package se.ingenuity.peakon.di.databinding

import androidx.databinding.DataBindingComponent
import dagger.Component
import se.ingenuity.peakon.databinding.ImageViewBindingAdapter
import se.ingenuity.peakon.di.AppComponent

@BindingScope
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
abstract class BindingComponent : DataBindingComponent {
    abstract override fun getImageViewBindingAdapter(): ImageViewBindingAdapter
}