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

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xxh.jetpacksample.JApplication
import com.xxh.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentAddItemBinding
import com.xxh.jetpacksample.room.codelab.data.database.inventory.Item
import kotlinx.coroutines.launch

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddItemFragment : BaseFragment<FragmentAddItemBinding>() {

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    lateinit var mItem: Item
    private var mItemId: Int = -1

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as JApplication).databaseApp.itemDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItemId = navigationArgs.itemId
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentAddItemBinding {
        return  FragmentAddItemBinding.inflate(inflater, container, false)
    }


    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            mBinding.itemName.text.toString(),
            mBinding.itemPrice.text.toString(),
            mBinding.itemCount.text.toString(),
        )
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                mBinding.itemName.text.toString(),
                mBinding.itemPrice.text.toString(),
                mBinding.itemCount.text.toString(),
            )
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateItem() {
        if (isEntryValid()) {
            val updateItem = mItem.copy(
                itemName = mBinding.itemName.text.toString(),
                itemPrice = mBinding.itemPrice.text.toString().toDouble(),
                quantityInStock = mBinding.itemCount.text.toString().toInt()
            )
            viewModel.updateItem(updateItem)
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }


    /**
     * Called when the view is created.
     * The itemId Navigation argument determines the edit item  or add new item.
     * If the itemId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.saveAction.setOnClickListener {
            if (isAdd()) {
                addNewItem()
            } else {
                updateItem()
            }
        }
        if (!isAdd()) {
            lifecycleScope.launch {
                viewModel.getItemById(mItemId).collect {
                    mItem = it
                    mBinding.itemName.text?.append(it.itemName)
                    mBinding.itemPrice.text?.append(it.itemPrice.toString())
                    mBinding.itemCount.text?.append(it.quantityInStock.toString())
                }
            }
        }
    }

    private fun isAdd(): Boolean {
        return mItemId < 0
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}
