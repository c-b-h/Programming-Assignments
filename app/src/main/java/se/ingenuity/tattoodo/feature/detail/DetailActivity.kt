package se.ingenuity.tattoodo.feature.detail

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import se.ingenuity.tattoodo.R
import se.ingenuity.tattoodo.databinding.ActivityDetailBinding
import se.ingenuity.tattoodo.model.PostDetail
import se.ingenuity.tattoodo.vo.Resource
import se.ingenuity.tattoodo.vo.Status
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {
    @dagger.Module
    interface Module {
        @ContributesAndroidInjector(modules = [DetailFragment.Module::class])
        fun contributeDetailFragment(): DetailFragment
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        postponeEnterTransition()

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        if (savedInstanceState == null) {
            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(
                        DetailFragment.ARG_POST_ID,
                        intent.getLongExtra(DetailFragment.ARG_POST_ID, 0L)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(binding.itemDetailContainer.id, fragment)
                .commitNow()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        ViewModelProviders
            .of(supportFragmentManager.findFragmentById(binding.itemDetailContainer.id)!!, viewModelFactory)
            .get(DetailViewModel::class.java)
            .detail
            .observe(this, Observer<Resource<PostDetail>> { detailResource ->
                binding.root.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        if (detailResource.status != Status.LOADING) {
                            binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                            startPostponedEnterTransition()
                        }

                        return true
                    }
                })
            })
    }

    companion object {
        const val INTENT_EXTRA_POST_ID = "post_id"
    }
}
