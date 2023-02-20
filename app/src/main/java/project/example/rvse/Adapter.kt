package project.example.rvse

import android.os.SystemClock
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
                     private val items: List<Model>?
    ): RecyclerView.ViewHolder(binding.root), ViewHolderWithDetails<Model> {

        override fun getItemDetail(): ItemDetailsLookup.ItemDetails<Model> {
            return ModelDetails(adapterPosition, items?.getOrNull(adapterPosition))
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        return HolderView(
            TemplateBinding.inflate(LayoutInflater.from(parent.context),
                parent, false
            ),
            null
        )
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) = Unit


    override fun onBindViewHolder(holder: HolderView, position: Int, payloads: List<Any>) {
        holder.setActivated(tracker.isSelected(array[position]))


        //holder.itemView.onClick {
            if (tracker.isSelected(array[position])) {
                holder.itemView.setOnClickListener {
                    tracker.deselect(array[position])
                    holder.itemView.setBackgroundColor(0)
                }
            } else {
                holder.itemView.setOnClickListener {
                    tracker.select(array[position])
                    holder.itemView.setBackgroundColor(R.drawable.ic_launcher_background)
                }
            }
        //}


        if (!tracker.hasSelection()) holder.itemView.setBackgroundColor(0)

        if (SelectionTracker.SELECTION_CHANGED_MARKER !in payloads) {
            holder.bind(array[position])
        }
    }

    private inline fun View.onClick(delayMillis: Long = 500, crossinline clickListener: (View) -> Unit) {
        var clickMillis = 0L

        setOnLongClickListener {
            val elapsedRealTime = SystemClock.elapsedRealtime()

            if (elapsedRealTime > clickMillis) {
                clickMillis = elapsedRealTime + delayMillis
                clickListener.invoke(it)
            }
            true
        }

    }

    override fun getItemCount() = array.size
}
