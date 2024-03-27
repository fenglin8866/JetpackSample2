package com.xxh.jetpacksample.room.codelab.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xxh.jetpacksample.databinding.ItemListItemBinding
import com.xxh.jetpacksample.room.codelab.database.inventory.Item

class ItemListAdapter(private val itemClick:(Item)->Unit) : ListAdapter<Item, ItemListAdapter.ItemListViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val mBinding =
            ItemListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder=ItemListViewHolder(mBinding)
        viewHolder.itemView.setOnClickListener {
            itemClick(getItem(viewHolder.bindingAdapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
            holder.bind(getItem(position))
    }

    class ItemListViewHolder(private val binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemName.text = item.itemName
            binding.itemPrice.text = item.itemPrice.toString()
            binding.itemQuantity.text = item.quantityInStock.toString()
        }
    }
}