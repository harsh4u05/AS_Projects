package com.example.projectmodules01.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.projectmodules01.R
import com.example.projectmodules01.databinding.FragmentProfileBinding
import com.example.projectmodules01.login.LoginFragment
import com.example.projectmodules01.login.LoginViewModel
import com.example.projectmodules01.login.LoginViewState
import com.example.utilitylibrary.FirebaseUtil
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var button: Button
    private var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSavedStateHandle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       // val root = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

     //   getSavedStateHandle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
           // Log.d(TAG, "onCreateView() button.setOnClickListener User is logged OUT. Going to Main Fragment")

            FirebaseUtil().getAuthUI().signOut(requireContext())
            Timber.d("------------ User is LOGGED_OUT.")
            navigateToStartDestination()
        }

        viewModel.authenticationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginViewState.LoginSuccess -> {
                    Timber.d("----------- User is Logged - observerAuth. GoTo Profile")
                    showUserContent()
                }
                else -> {
                    Timber.d("User not Logged-in. Goto Login")
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
            }

        }

    }

    private fun getSavedStateHandle() {

        num++
        val currentBackStackEntry = findNavController().currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle

        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                if (success) {
                    Timber.d("Login Success : - SavedStateHandle. Goto Profile")
                } else {
                    Timber.d("Login Error : - Value from SavedStateHandle.")
                    navigateToStartDestination()
                }
            }
        Timber.d("--------------->> Harsh savedStateHandle calle $num of times.")
    }

    private fun showUserContent() {
       // val text = String.format(resources.getString(R.string.showUserContent), "${FirebaseAuth.getInstance().currentUser?.displayName}")
       // binding.textView.text = "showUserContent() : User ${FirebaseAuth.getInstance().currentUser?.displayName} is logged."
        binding.textView.text = String.format(resources.getString(R.string.showUserContent), "${FirebaseAuth.getInstance().currentUser?.displayName}")
    }

    fun navigateToStartDestination() {

        val startDestination = findNavController().graph.startDestination
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()

        findNavController().navigate(startDestination, null, navOptions)
    }

    //    private fun loggedUserContent() {
//        FirestoreOperations().uploadData()
//        FirestoreOperations().readData()
//        textView.text = "Successfully Logged-in"
//    }

}