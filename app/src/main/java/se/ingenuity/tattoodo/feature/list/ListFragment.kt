package se.ingenuity.tattoodo.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Binds
import dagger.android.support.DaggerFragment
import dagger.multibindings.IntoMap
import se.ingenuity.tattoodo.databinding.FragmentListBinding
import se.ingenuity.tattoodo.lifecycle.ViewModelKey
import se.ingenuity.tattoodo.model.Posts
import se.ingenuity.tattoodo.vo.Resource
import javax.inject.Inject

class ListFragment : DaggerFragment() {
    @dagger.Module
    interface Module {
        @Binds
        @IntoMap
        @ViewModelKey(ListViewModel::class)
        fun bindViewModel(viewModel: ListViewModel): ViewModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.itemList.layoutManager = LinearLayoutManager(binding.itemList.context)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navigationViewModel = ViewModelProviders
            .of(requireActivity())
            .get(NavigationViewModel::class.java)

        ListAdapter().apply {
            onPostClickListener = { postId: Long, transitionTags: Array<String> ->
                navigationViewModel.userClicksOnPost(postId, transitionTags)
            }
        }.also {
            binding.itemList.adapter = it

            ViewModelProviders
                .of(this, viewModelFactory)
                .get(ListViewModel::class.java)
                .posts
                .observe(this, Observer<Resource<Posts>> { postsResource ->
                    it.posts = postsResource
                })
        }
    }

}