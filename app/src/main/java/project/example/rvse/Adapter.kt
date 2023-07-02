package project.example.rvse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import project.example.rvse.databinding.TemplateBinding

class Adapter: RecyclerView.Adapter<Adapter.HolderView>() {
    val array = ArrayModels.array
    
    lateinit var tracker: SelectionTracker<Model>

    class HolderView(private var binding: TemplateBinding,
                     private val items: List<Model>?,
                     listener: Listener?
    ): RecyclerView.ViewHolder(binding.root), ViewHolderWithDetails<Model> {

        override fun getItemDetail(): ItemDetailsLookup.ItemDetails<Model> {
            return ModelDetails(adapterPosition, items?.getOrNull(adapterPosition))
        }

        init {
            itemView.setOnClickListener {
                listener?.onClick(adapterPosition, itemView)
            }

            itemView.setOnLongClickListener {
                listener?.onLongClick(adapterPosition, itemView)
                true
            }
        }

        var index = 0
        fun bind(model: Model) {
            model.id = index++
            binding.title.text = model.title
            binding.text.text = model.text
        }

        fun setActivated(isActivated: Boolean) {
            itemView.isActivated = isActivated
        }

        interface Listener {
            fun onClick(position: Int, view: View)
            fun onLongClick(position: Int, view: View)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        return HolderView(
            TemplateBinding.inflate(LayoutInflater.from(parent.context),
                parent, false
            ),
            null,
            null
        )
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) = Unit

    override fun onBindViewHolder(holder: HolderView, position: Int, payloads: List<Any>) {
        holder.setActivated(tracker.isSelected(array[position]))
        if (!tracker.hasSelection()) holder.itemView.setBackgroundColor(0)

        if (!tracker.isSelected(array[position])) {
            holder.itemView.setOnClickListener {
                tracker.select(array[position])
                holder.itemView.setBackgroundColor(R.drawable.ic_launcher_background)
            }
        } else {
            holder.itemView.setOnClickListener {
                tracker.deselect(array[position])
                holder.itemView.setBackgroundColor(0)
            }
        }

        if (SelectionTracker.SELECTION_CHANGED_MARKER !in payloads) {
            holder.bind(array[position])
        }
    }

    override fun getItemCount() = array.size
}
