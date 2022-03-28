package com.kimdo.roomdbtest.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kimdo.roomdbtest.databinding.FragmentMainBinding
import com.kimdo.roomdbtest.model.LoginState
import com.kimdo.roomdbtest.viewmodel.MainViewModel


class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate( inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.usernameTV.text = LoginState.user?.username

        binding?.signoutBtn!!.setOnClickListener { onSignout() }
        binding?.deleteUserBtn!!.setOnClickListener { onDelete() }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observeViewModel()
    }
    fun observeViewModel() {
        viewModel.signout.observe(viewLifecycleOwner) {
            Toast.makeText(activity, "Signed out", Toast.LENGTH_SHORT).show()
            gotoSignupScreen()
        }
        viewModel.userDeleted.observe( viewLifecycleOwner) {
            Toast.makeText(activity, "User deleted", Toast.LENGTH_SHORT).show()
            gotoSignupScreen()
        }
    }

    private fun gotoSignupScreen() {
        val action = MainFragmentDirections.actionGoToSignupMain()
        Navigation.findNavController(binding!!.usernameTV).navigate(action)
    }

    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete User")
                .setMessage("Are you sure tyou want to 삭제할거예요?")
                .setPositiveButton("Yes") {p0, p1 -> viewModel.onDeleteUser()}
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}