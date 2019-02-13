package se.ingenuity.tattoodo.feature.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import se.ingenuity.tattoodo.R
import se.ingenuity.tattoodo.databinding.ActivityMainBinding
import se.ingenuity.tattoodo.feature.detail.DetailActivity
import se.ingenuity.tattoodo.feature.detail.DetailFragment
import se.ingenuity.tattoodo.feature.list.ListFragment
import se.ingenuity.tattoodo.feature.list.Message
import se.ingenuity.tattoodo.feature.list.NavigationViewModel

class MainActivity : DaggerAppCompatActivity() {
    @dagger.Module
    interface Module {
        @ContributesAndroidInjector(modules = [ListFragment.Module::class])
        fun contributeListFragment(): ListFragment

        @ContributesAndroidInjector(modules = [DetailFragment.Module::class])
        fun contributeDetailFragment(): DetailFragment
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.itemListContainer.id, ListFragment())
                .commit()
        }

        ViewModelProviders
            .of(this)
            .get(NavigationViewModel::class.java)
            .getNavigateToDetails()
            .observe(this, Observer<Message<Pair<Long, Array<String>>>> {
                it.getContentIfUnhandled()?.let { message ->
                    binding.itemDetailContainer?.id?.let { detailContainerId ->
                        supportFragmentManager
                            .beginTransaction()
                            .replace(detailContainerId, DetailFragment().apply {
                                arguments = Bundle().apply {
                                    putLong(DetailFragment.ARG_POST_ID, message.first)
                                }
                            })
                            .commit()
                    } ?: run {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.INTENT_EXTRA_POST_ID, message.first)
                        }

                        val bundle = ActivityOptions
                            .makeSceneTransitionAnimation(this, *createTransitions(message.second))
                            .toBundle()

                        startActivity(intent, bundle)
                    }
                }
            })
    }

    private fun createTransitions(transitionTags: Array<String>): Array<android.util.Pair<View, String>> {
        val transitions = mutableListOf<android.util.Pair<View, String>>()
        transitionTags.forEach { tag ->
            binding.root.findViewWithTag<View>(tag)?.let {
                transitions.add(android.util.Pair.create(it, it.transitionName))
            }
        }

        return transitions.toTypedArray()
    }
}
