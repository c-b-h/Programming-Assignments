package se.ingenuity.peakon.feature.search

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import se.ingenuity.peakon.databinding.ViewSearchEntryBinding
import java.text.Normalizer

class SearchAdapter(
    context: Context,
    searchEntries: List<SearchEntry>
) : ArrayAdapter<SearchEntry>(context, 0, View.NO_ID, searchEntries.toList()) {

    private val lock = Any()

    private val originalEntries by lazy(lock) {
        searchEntries.toList()
    }

    private val searchFilter by lazy {
        SearchFilter()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = convertView?.let {
            DataBindingUtil.getBinding<ViewSearchEntryBinding>(it)
        } ?: ViewSearchEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        with(binding) {
            searchEntry = getItem(position)
            executePendingBindings()
        }

        return binding.root
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private inner class SearchFilter : Filter() {
        override fun performFiltering(prefix: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()

            if (prefix == null || prefix.isEmpty()) {
                val list = synchronized(lock) {
                    originalEntries.toList()
                }
                results.values = list
                results.count = list.size
            } else {
                val prefixString = Normalizer.normalize(prefix, Normalizer.Form.NFD)
                    .replace("\\p{InCombiningDiacriticalMarks}+", "")

                val entries = synchronized(lock) {
                    originalEntries.toList()
                }

                val newEntries = mutableListOf<SearchEntry>()
                entries.forEach { searchEntry ->
                    if (searchEntry.name.startsWith(prefixString, true) ||
                        searchEntry.email.startsWith(prefixString, true)
                    ) {
                        newEntries.add(
                            searchEntry.copy(
                                name = highlight(prefixString, searchEntry.name),
                                email = highlight(prefixString, searchEntry.email)
                            )
                        )
                    } else {
                        val nameParts = searchEntry.name.splitToSequence(' ')
                        for (namePart in nameParts) {
                            if (namePart.startsWith(prefixString, true)) {
                                newEntries.add(
                                    searchEntry.copy(
                                        name = highlight(prefixString, searchEntry.name)
                                    )
                                )
                            }
                        }
                    }
                }

                results.values = newEntries
                results.count = newEntries.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            setNotifyOnChange(false)
            clear()
            addAll(results.values as List<SearchEntry>)
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        private fun highlight(constraint: String, text: CharSequence): CharSequence {
            val normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+", "")

            var indexOfConstraint = normalizedText.indexOf(constraint, 0, true)
            if (indexOfConstraint < 0) {
                return text
            } else {
                val highlightedText = SpannableString(text)

                while (indexOfConstraint >= 0) {
                    val spanStart = Math.min(indexOfConstraint, text.length)
                    val spanEnd = Math.min(indexOfConstraint + constraint.length, text.length)
                    if (spanEnd > spanStart) {
                        highlightedText.setSpan(
                            StyleSpan(Typeface.BOLD),
                            spanStart,
                            spanEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

                    indexOfConstraint = normalizedText.indexOf(constraint, spanEnd, true)
                }
                return highlightedText
            }
        }
    }
}