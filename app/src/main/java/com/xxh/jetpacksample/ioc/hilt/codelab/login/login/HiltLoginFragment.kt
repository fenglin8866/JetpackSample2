package com.xxh.jetpacksample.ioc.hilt.codelab.login.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.xxh.jetpacksample.R
import com.xxh.common.BaseFragment
import com.xxh.jetpacksample.databinding.FragmentHiltLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltLoginFragment : BaseFragment<FragmentHiltLoginBinding>() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHiltLoginBinding {
        return FragmentHiltLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginSuccess -> {
                    findNavController().navigate(R.id.action_hiltLoginFragment_to_hiltLoginModuleFragment)
                }

                is LoginError -> mBinding.error.visibility = View.VISIBLE
            }
        }

        setupViews()
    }

    private fun setupViews() {
        val usernameEditText = mBinding.username
        usernameEditText.isEnabled = false
        usernameEditText.setText(loginViewModel.getUsername())

        val passwordEditText = mBinding.password
        passwordEditText.doOnTextChanged { _, _, _, _ -> mBinding.error.visibility = View.INVISIBLE }

        mBinding.login.setOnClickListener {
            loginViewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }
        mBinding.unregister.setOnClickListener {
            loginViewModel.unregister()
            /*val intent = Intent(this, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)*/
            findNavController().navigate(R.id.action_hiltLoginFragment_to_enterDetailsFragment)
        }
    }

}

sealed class LoginViewState
object LoginSuccess : LoginViewState()
object LoginError : LoginViewState()