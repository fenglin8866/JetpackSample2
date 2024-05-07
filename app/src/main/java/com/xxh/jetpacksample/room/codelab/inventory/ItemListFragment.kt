/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xxh.jetpacksample.room.codelab.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.R
import com.xxh.common.BaseFragment
import com.xxh.jetpacksample.databinding.ItemListFragmentBinding
import kotlinx.coroutines.launch

/**
 * Main fragment displaying details for all items in the database.
 */
class ItemListFragment : BaseFragment<ItemListFragmentBinding>() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as JApplication).databaseApp.itemDao()
        )
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemListFragmentBinding {
        return ItemListFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        val itemListAdapter = ItemListAdapter{
            val directions=ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
            this.findNavController().navigate(directions)
        }
        mBinding.recyclerView.adapter = itemListAdapter
        mBinding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
        lifecycleScope.launch {
            viewModel.getAllItem().collect{
                itemListAdapter.submitList(it)
            }
        }
    }
}
