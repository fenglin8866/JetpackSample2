package com.xxh.jetpacksample.room.codelab.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.R
import com.xxh.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentWordsBinding

class WordsFragment : BaseFragment<FragmentWordsBinding>() {

    private val wordViewModel: WordViewModel by activityViewModels{
        WordViewModelFactory((activity?.application as JApplication).repository)
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentWordsBinding {
        return FragmentWordsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WordListAdapter()
        mBinding.recyclerview.adapter= adapter
        mBinding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        mBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_wordsFragment_to_newWordFragment)
        }


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        //活跃状态更新，导致界面闪动
        wordViewModel.allWords.observe(viewLifecycleOwner){
                words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
        /*lifecycleScope.launch {
            wordViewModel.allWords.collect{
                    words ->
                words.let { adapter.submitList(it) }
            }
        }*/

    }
}