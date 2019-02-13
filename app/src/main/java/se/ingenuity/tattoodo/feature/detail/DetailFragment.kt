package se.ingenuity.tattoodo.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.android.support.DaggerFragment
import dagger.multibindings.IntoMap
import se.ingenuity.tattoodo.databinding.FragmentDetailBinding
import se.ingenuity.tattoodo.lifecycle.ViewModelKey
import se.ingenuity.tattoodo.model.PostDetail
import se.ingenuity.tattoodo.vo.Resource
import javax.inject.Inject

class DetailFragment : DaggerFragment() {
    @dagger.Module
    interface Module {
        @Binds
        @IntoMap
        @ViewModelKey(DetailViewModel::class)
        fun bindViewModel(viewModel: DetailViewModel): ViewModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val postId: Long by lazy {
        arguments!!.getLong(ARG_POST_ID)
    }

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewModelProviders
            .of(this, viewModelFactory)
            .get(DetailViewModel::class.java)
            .apply {
                init(postId)
            }
            .detail
            .observe(this, Observer<Resource<PostDetail>> { detailResource ->
                with(binding) {
                    post = detailResource.data?.data
                    status = detailResource.status
                    executePendingBindings()
                }
            })
    }

    companion object {
        const val ARG_POST_ID = "post_id"
    }
}
