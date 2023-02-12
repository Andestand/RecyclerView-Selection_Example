package project.example.rvse

import androidx.recyclerview.selection.ItemKeyProvider

class ModelKeyProvider(private val items: List<Model>): ItemKeyProvider<Model>(ItemKeyProvider.SCOPE_CACHED) {
    override fun getKey(position: Int): Model? {
        return items.getOrNull(position)
    }

    override fun getPosition(key: Model): Int {
        return items.indexOf(key)
    }

}
