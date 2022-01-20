package com.example.loginmodule.login.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.loginmodule.login.R
import com.example.loginmodule.login.databinding.FragmentLoginBinding
import com.example.loginmodule.userManager.UserManager
import com.example.utilitylibrary.FirebaseUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var userManager: UserManager
    @Inject
    lateinit var firebaseUtil: FirebaseUtil

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("------------- LoginFragment : Is user logged from UserManager : ", "${userManager.getUserLoginStatus()}")
        //firebaseUtil.setEmulators()
        registerSignInLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.logoutButton.setOnClickListener {
//            FirebaseUtil().getAuthUI().signOut(this)
            userManager.logout()
            Log.d("------------- Harsh after logout User in UserManager : ", "${userManager.getUserLoginStatus()}")
        }
        binding.loginButton.setOnClickListener {
            launchSignInLauncher()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerAuth()
    }

    private fun observerAuth() {
        viewModel.authState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                LoginViewState.LoginSuccess -> {
                    Log.d("----------- LoginFragment Login Success: viewModel.authState.observe", "User Login")
                }
                else -> {
                    Log.d("----------- LoginFragment Login Error: viewModel.authState.observe", "User Login")
                }
            }

        })
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

        val signInIntent = firebaseUtil.getAuthUI()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse

        if (result.resultCode == AppCompatActivity.RESULT_OK) {

            Log.d("-----------LoginFragment : ", "User from userManager successfully logged : ${userManager.username}")

        } else {

            Log.e("----------- : Harsh1 : ", "User login error. : ")

            if ( response == null ) {
                Log.e("-----------Harsh2 : ", "User pressed back button")
            } else {
            }

            Log.e("-----------Harsh3 : ", "User Login error : ${response?.error?.errorCode}")

        }
    }

}