package se.ingenuity.tattoodo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import se.ingenuity.tattoodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.content.itemListContainer.id, ItemListFragment())
                .commit()
        }

        ViewModelProviders
            .of(this)
            .get(ItemListViewModel::class.java)
            .getNavigateToDetails()
            .observe(this, Observer<Event<String>> {
                it.getContentIfUnhandled()?.let { id ->
                    binding.content.itemDetailContainer?.id?.let { detailContainerId ->
                        supportFragmentManager
                            .beginTransaction()
                            .replace(detailContainerId, ItemDetailFragment().apply {
                                arguments = Bundle().apply {
                                    putString(ItemDetailFragment.ARG_ITEM_ID, id)
                                }
                            })
                            .commit()
                    } ?: run {
                        val intent = Intent(this@MainActivity, ItemDetailActivity::class.java).apply {
                            putExtra(ItemDetailFragment.ARG_ITEM_ID, id)
                        }
                        startActivity(intent)
                    }
                }
            })
    }
}
