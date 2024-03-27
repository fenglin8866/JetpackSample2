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
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentItemDetailBinding
import com.xxh.jetpacksample.room.codelab.data.database.inventory.Item
import kotlinx.coroutines.launch

/**
 * [ItemDetailFragment] displays the details of the selected item.
 */
class ItemDetailFragment : BaseFragment<FragmentItemDetailBinding>() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as JApplication).databaseApp.itemDao()
        )
    }

    private var mItemId: Int = 0
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    private lateinit var mItem: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItemId = navigationArgs.itemId
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentItemDetailBinding {
        return FragmentItemDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.deleteItem.setOnClickListener {
            showConfirmationDialog()
        }
        mBinding.editItem.setOnClickListener {
            val directions = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
                "Edit",
                mItemId
            )
            findNavController().navigate(directions)
        }
        lifecycleScope.launch {
            viewModel.getItemById(mItemId).collect {
                mItem = it
                //todo 是否可以在suspend类中更新界面
                mBinding.itemName.text = it.itemName
                mBinding.itemPrice.text = it.itemPrice.toString()
                mBinding.itemCount.text = it.quantityInStock.toString()
            }
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.delItem(mItem)
        findNavController().navigateUp()
    }

}
