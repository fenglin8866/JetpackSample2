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
import com.xxh.jetpacksample.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentNewWordBinding
import com.xxh.jetpacksample.room.codelab.data.database.word.Word


/**
 * A simple [Fragment] subclass.
 * Use the [NewWordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewWordFragment : BaseFragment<FragmentNewWordBinding>() {
    private val wordViewModel: WordViewModel by activityViewModels {
        WordViewModelFactory((activity?.application as JApplication).repository)
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentNewWordBinding {
        return FragmentNewWordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonSave.setOnClickListener {
            val word=mBinding.editWord.text.toString()
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

}