package project.example.rvse

import androidx.recyclerview.selection.ItemDetailsLookup

class ModelDetails(private val postionAdapter: Int, private val selectedKey: Model?): ItemDetailsLookup.ItemDetails<Model>() {
    override fun getPosition(): Int {
        return postionAdapter
    }

    override fun getSelectionKey(): Model? {
        return selectedKey
    }
}