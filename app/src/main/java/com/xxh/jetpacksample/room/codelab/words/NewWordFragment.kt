package com.xxh.jetpacksample.room.codelab.words

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.JApplication
import com.xxh.jetpacksample.R
import com.xxh.jetpacksample.databinding.FragmentNewWordBinding
import com.xxh.jetpacksample.room.codelab.database.word.Word


/**
 * A simple [Fragment] subclass.
 * Use the [NewWordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewWordFragment : Fragment() {
    private val wordViewModel: WordViewModel by activityViewModels {
        WordViewModelFactory((activity?.application as JApplication).repository)
    }

    private var _binding: FragmentNewWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            val word=binding.editWord.text.toString()
            if (word.isBlank()) {
                Toast.makeText(
                    activity,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                wordViewModel.insert(Word(word))
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}