package com.example.projectmodules01.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.loginmodule.login.databinding.FragmentLoginBinding
import com.example.projectmodules01.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

//testing for git
@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private lateinit var savedStateHandle: SavedStateHandle
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
   // private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerSignInLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    //    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
//        binding.logoutButton.setOnClickListener {
//            userManager.logout()
//
//        }
//        binding.loginButton.setOnClickListener {
//            launchSignInLauncher()
//        }
   //     return binding.root
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)
        observerAuth()
        launchSignInLauncher()
    }

    private fun observerAuth() {
        viewModel.authenticationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginViewState.LoginSuccess -> {
                    savedStateHandle.set(LOGIN_SUCCESSFUL, true)
                    Timber.d("----------- LoginFragment User is Logged - observerAuth. GoTo Profile")
                    destinationAfterLogin()
                }
                else -> {
                    savedStateHandle.set(LOGIN_SUCCESSFUL, false)
                    Timber.d("-------------User not Logged-in.")
                }
            }

        }
    }

    private fun registerSignInLauncher() {
        signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result ->
            onSignInResult(result)
        }
    }

    private fun launchSignInLauncher() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse

        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            savedStateHandle.set(LOGIN_SUCCESSFUL, true)
            Timber.d("----------------User successfully logged")
            destinationAfterLogin()
        } else {
            savedStateHandle.set(LOGIN_SUCCESSFUL, false)
            if ( response == null ) {
                Timber.e("-----------Harsh :User pressed back button")
                navigateToStartDestination()
            }

            Timber.e("-----------Harsh3 : User Login error : ${response?.error?.errorCode}")
            navigateToStartDestination()

        }
    }

    private fun navigateToStartDestination() {
        val startDestination = findNavController().graph.startDestination
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()
        findNavController().navigate(startDestination, null, navOptions)
    }

    private fun destinationAfterLogin() {
        val currentBackStackEntry = findNavController().currentBackStackEntry

        savedStateHandle.getLiveData<Boolean>(LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry!!) { success ->
                if (success) {
                    findNavController().popBackStack()
                } else {
                    Timber.d("------------>Harsh Login error. Go Back to Login")
                    launchSignInLauncher()
                }
            }
    }

}
