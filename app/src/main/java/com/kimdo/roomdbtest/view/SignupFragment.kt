package com.kimdo.roomdbtest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kimdo.roomdbtest.databinding.FragmentSignupBinding
import com.kimdo.roomdbtest.viewmodel.SignupViewModel


class SignupFragment : Fragment() {

    private var binding:FragmentSignupBinding? = null
    private lateinit var viewModel: SignupViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.signupBtn.setOnClickListener { onSignup(it) }
        binding!!.gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        observeViewModel()
    }
    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner) { isComplete ->

            val action = SignupFragmentDirections.actionGoToMainSignup()
            Navigation.findNavController(binding!!.signupUsername).navigate( action )

        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(activity, "Error $error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSignup(v:View) {
        val username = binding!!.signupUsername.text.toString()
        val password = binding!!.signupPassword.text.toString()
        val info = binding!!.otherInfo.text.toString()
        if(username.isNullOrEmpty() || password.isNullOrEmpty() || info.isNullOrEmpty()) {
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.signup(username, password, info)
        }
    }

    private fun onGotoLogin(v:View) {
        val action = SignupFragmentDirections.actionGoToLoginSignup()
        Navigation.findNavController(v).navigate( action )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}