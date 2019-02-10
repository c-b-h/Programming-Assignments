package se.ingenuity.tattoodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import se.ingenuity.tattoodo.databinding.FragmentItemListBinding
import se.ingenuity.tattoodo.databinding.ItemListContentBinding
import se.ingenuity.tattoodo.dummy.DummyContent

class ItemListFragment : Fragment() {
    private lateinit var binding: FragmentItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders
            .of(requireActivity())
            .get(ItemListViewModel::class.java)

        Adapter().apply {
            onClickListener = View.OnClickListener { v ->
//                val item = v.tag as DummyContent.DummyItem
                viewModel.userClicksOnButton("asdasd")
            }
        }.also {
            binding.itemList.adapter = it

            //TODO: Load async
            it.values = DummyContent.ITEMS
        }
    }

    private class Adapter() : RecyclerView.Adapter<Adapter.ViewHolder>() {

        var onClickListener = View.OnClickListener { }

        var values = emptyList<DummyContent.DummyItem>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent, onClickListener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(values[position])
        }

        override fun getItemCount() = values.size

        private class ViewHolder(parent: ViewGroup, onClickListener: View.OnClickListener) : RecyclerView.ViewHolder(
            ItemListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).root
        ) {

            init {
                itemView.setOnClickListener(onClickListener)
            }

            fun bind(item: DummyContent.DummyItem) {
                with(
                    DataBindingUtil.getBinding<ItemListContentBinding>(
                        itemView
                    )!!
                ) {
                    idText.text = item.id
                    content.text = item.content
                }
            }
        }
    }
}