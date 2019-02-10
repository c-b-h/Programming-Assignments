package se.ingenuity.peakon.feature.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import se.ingenuity.peakon.R
import se.ingenuity.peakon.databinding.ActivitySearchBinding
import se.ingenuity.peakon.databinding.EmployeeDetailsBinding
import se.ingenuity.peakon.lifecycle.ViewModelKey
import se.ingenuity.peakon.vo.Status
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity() {
    @dagger.Module
    interface Module {
        @Binds
        @IntoMap
        @ViewModelKey(SearchViewModel::class)
        fun bindViewModel(viewModel: SearchViewModel): ViewModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(SearchViewModel::class.java)

        viewModel.searchEntries
            .observe(this, Observer { searchEntriesResource ->
                if (searchEntriesResource.status == Status.SUCCESS) {
                    binding.input.setAdapter(SearchAdapter(this@SearchActivity, searchEntriesResource.data!!))
                }

                binding.progressIndicator.visibility = if (searchEntriesResource.status == Status.LOADING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            })

        viewModel.employeeDetails
            .observe(this, Observer {
                if (it.first == null) {
                    return@Observer
                }

                val contentBinding = binding.content.viewStub?.run {
                    DataBindingUtil.getBinding<EmployeeDetailsBinding>(inflate())
                } ?: binding.content.binding as EmployeeDetailsBinding

                contentBinding.employee = it.first
                contentBinding.account = it.second
                contentBinding.invalidateAll()
            })

        binding.input.setOnItemClickListener { parent, _, position, _ ->
            with(getSystemService(InputMethodManager::class.java)) {
                hideSoftInputFromWindow(window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
            viewModel.employeeId((parent.adapter.getItem(position) as SearchEntry).id)
        }
    }
}
