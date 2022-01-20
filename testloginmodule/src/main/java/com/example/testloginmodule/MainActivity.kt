package com.example.testloginmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.testloginmodule.databinding.ActivityMainBinding
import com.example.utilitylibrary.FirebaseUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

  //  private lateinit var firebaseUtil: FirebaseUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
      //  binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        val loginButton = findViewById<Button>(R.id.loginButton)
//        val logoutButton = findViewById<Button>(R.id.logoutButton)
//        val textView = findViewById<TextView>(R.id.welcome_text)
        binding.welcomeText.text = "Cool"
        binding.loginButton.setOnClickListener {
            launchSignInLauncher()
        }
        binding.logoutButton.setOnClickListener {
            FirebaseUtil().getAuthUI().signOut(this)
        }
        FirebaseUtil().setEmulators()
        registerSignInLauncher()

    }

    override fun onStart() {
        super.onStart()
        observerAuth()
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

        val signInIntent = FirebaseUtil().getAuthUI()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse

        if (result.resultCode == AppCompatActivity.RESULT_OK) {

            val user = FirebaseUtil().getAuth().currentUser

            Log.d("-----------MainActivity : ", "User from userManager successfully logged : $user")

        } else {

            Log.e("----------- : MainActivity1 : ", "User login error. : ")

            if ( response == null ) {
                Log.e("----------- MainActivity2 : ", "User pressed back button")
            } else {
            }

            Log.e("----------- MainActivity3 : ", "User Login error : ${response?.error?.errorCode}")

        }
    }

    private fun observerAuth() {
        viewModel.authState.observe(this, Observer { state ->
            when (state) {
                LoginViewState.LoginSuccess -> {
                    Log.d("----------- MainActivity Login Success: viewModel.authState.observe", "User Login")
            }
                else -> {
                    Log.d("----------- MainActivity Login Error: viewModel.authState.observe", "User Login")
                }
            }

        })
    }
}