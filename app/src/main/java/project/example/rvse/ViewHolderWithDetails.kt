package project.example.rvse

import androidx.recyclerview.selection.ItemDetailsLookup

interface ViewHolderWithDetails<Item> {
    fun getItemDetail(): ItemDetailsLookup.ItemDetails<Item>
}
