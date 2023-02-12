package project.example.rvse


import android.annotation.SuppressLint
import android.print.PrinterInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class Adapter: RecyclerView.Adapter<Adapter.HolderView>() {
    val array = listOf(
        Model(1,"Зоголово 1", "Описание 1"), Model(11, "Зоголово 2", "Описание 2"),
        Model(2, "Зоголово 3", "Описание 3"), Model(12,"Зоголово 4", "Описание 4"),
        Model(3, "Зоголово 5", "Описание 5"), Model(13, "Зоголово 6", "Описание 6"),
        Model(4, "Зоголово 7", "Описание 7"), Model(14, "Зоголово 8", "Описание 8"),
        Model(5, "Зоголово 9", "Описание 9"), Model(15, "Зоголово 10", "Описание 10"),
        Model(6, "Зоголово 11", "Описание 11"), Model(16,"Зоголово 12", "Описание 12"),
        Model(7, "Зоголово 13", "Описание 13"), Model(17,"Зоголово 14", "Описание 14"),
        Model(8, "Зоголово 15", "Описание 15"), Model(18,"Зоголово 16", "Описание 16"),
        Model(9, "Зоголово 17", "Описание 17"), Model(19,"Зоголово 18", "Описание 18"),
        Model(10, "Зоголово 19", "Описание 19"), Model(10,"Зоголово 20", "Описание 20")
    )
    
    lateinit var tracker: SelectionTracker<Model>


    class HolderView(item: View, private val items: List<Model>?): RecyclerView.ViewHolder(item), ViewHolderWithDetails<Model> {
        private val title = item.findViewById<TextView>(R.id.title)
        private val text = item.findViewById<TextView>(R.id.text)

        override fun getItemDetail(): ItemDetailsLookup.ItemDetails<Model> {
            return ModelDetails(adapterPosition, items?.getOrNull(adapterPosition))
        }
        var index = 0
        fun bind(model: Model) {
            model.id = index++
            title.text = model.title
            text.text = model.text
        }

        fun setActivated(isActivated: Boolean) {
            itemView.isActivated = isActivated
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        return HolderView(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.template, parent, false),
            null
        )
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) = Unit

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: HolderView, position: Int, payloads: List<Any>) {
        holder.setActivated(tracker.isSelected(array[position]))

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

        if (!tracker.hasSelection()) holder.itemView.setBackgroundColor(0)

        if (SelectionTracker.SELECTION_CHANGED_MARKER !in payloads) {
            holder.bind(array[position])
        }
    }

    override fun getItemCount() = array.size
}
