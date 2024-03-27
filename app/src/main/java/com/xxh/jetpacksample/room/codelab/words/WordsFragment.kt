package com.xxh.jetpacksample.room.codelab.words

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.databinding.FragmentWordsBinding
import kotlinx.coroutines.launch

class WordsFragment : Fragment() {


    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by activityViewModels{
        WordViewModelFactory((activity?.application as JApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentWordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WordListAdapter()
        binding.recyclerview.adapter= adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}