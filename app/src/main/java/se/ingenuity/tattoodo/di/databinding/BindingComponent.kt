package se.ingenuity.tattoodo.di.databinding

import androidx.databinding.DataBindingComponent
import dagger.Component
import se.ingenuity.tattoodo.databinding.ImageViewBindingAdapter
import se.ingenuity.tattoodo.di.AppComponent

@BindingScope
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
abstract class BindingComponent : DataBindingComponent {
    abstract override fun getImageViewBindingAdapter(): ImageViewBindingAdapter
}