package com.kimdo.roomdbtest.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kimdo.roomdbtest.databinding.FragmentLoginBinding
import com.kimdo.roomdbtest.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var viewModel:LoginViewModel

    private var binding:FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.loginBtn.setOnClickListener { onLogin(it) }
        binding!!.gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
//            .get(LoginViewModel::class.java)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe( viewLifecycleOwner ) { isComplete ->

            Toast.makeText(activity, "Login Complete", Toast.LENGTH_SHORT).show()
            val action = LoginFragmentDirections.actionGoToMain()
            Navigation.findNavController(binding!!.loginUsername).navigate( action )

        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    
    private fun onLogin(v:View) {
        val username = binding!!.loginUsername.text.toString()
        val password = binding!!.loginPassword.text.toString()
        if(username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(username, password)
        }
    }
    
    private fun onGotoSignup(v: View) {
        val action = LoginFragmentDirections.actionGoToSignupMain()
        Navigation.findNavController(v).navigate(action)
    }

    companion object {
        val TAG = "LoginFragment"
    }

}