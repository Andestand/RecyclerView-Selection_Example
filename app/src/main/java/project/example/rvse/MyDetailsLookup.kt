package project.example.rvse

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class MyDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Model>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Model>? {
        return recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as? ViewHolderWithDetails<Model>)?.getItemDetail()
        }
    }
}