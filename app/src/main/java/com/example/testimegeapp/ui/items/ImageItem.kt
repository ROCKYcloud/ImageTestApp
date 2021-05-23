package com.example.testimegeapp.ui.items

import android.view.View
import coil.load
import com.example.testimegeapp.databinding.ItemListBinding
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import com.example.testimegeapp.R

typealias OnTap = () -> Unit

class ImageItem(
    private val photoUrl:String,
    private val onTap: OnTap
) : BindableItem<ItemListBinding>() {

    override fun isSameAs(other: Item<*>): Boolean = other is ImageItem

    override fun hasSameContentAs(other: Item<*>) = other is ImageItem &&
            other.photoUrl == photoUrl

    override fun getLayout() = R.layout.item_list

    override fun initializeViewBinding(view: View) = ItemListBinding.bind(view)

    override fun bind(viewBinding: ItemListBinding, position: Int) {
        viewBinding.card.setOnClickListener { onTap() }
        viewBinding.imageView.load(photoUrl)
    }
}