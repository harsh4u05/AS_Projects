package com.example.loginmodule.login


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.loginmodule.storage.SharedPreferencesStorage
import com.example.loginmodule.userManager.UserManager
import com.example.utilitylibrary.FirebaseUtil
import com.example.utilitylibrary.FirestoreOperations
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

//    companion object {
//        const val TAG = "login.LoginActivity"
//    }
//
//    @Inject
//    lateinit var userManager: UserManager
//
//    @Inject
//    lateinit var firebaseUtil: FirebaseUtil
//
//    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

}
        /************
        //setContentView(R.layout.activity_login)
    //    userManager = UserManager(SharedPreferencesStorage(this))
      //  firebaseUtil = FirebaseUtil()
        firebaseUtil.setEmulators()
//        val signInLauncher = registerSignInLauncher()
//        signInLauncher.launch(launchSignInLauncher())
    //    registerSignInLauncher()
     //   launchSignInLauncher()
       // finish()
    }

    //
//    private fun registerSignInLauncher() : ActivityResultLauncher<Intent> {
//        return registerForActivityResult(
//            FirebaseAuthUIActivityResultContract()
//        ) { result ->
//            onSignInResult(result)
//        }
//    }

    private fun registerSignInLauncher() {
        signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result ->
            onSignInResult(result)
        }
    }

//    private fun launchSignInLauncher() : Intent {
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build()
//        )
//
//        return firebaseUtil.getAuthUI()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build()
//    }

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
    //

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        val response = result.idpResponse

        if (result.resultCode == RESULT_OK) {

            userManager.userJustLoggedIn()

            if(response!!.isNewUser) {
                registerNewUser()
            }

            Log.d("-----------$TAG : Harsh : ", "User successfully logged : ${firebaseUtil.getAuth().currentUser}")

        } else {

            if ( response == null ) {
                Log.e("-----------$TAG : Harsh : ", "User pressed back button")
            } else {
                Log.e("-----------$TAG : Harsh : ", "User Login error : ${response.error?.errorCode}")
            }

        }
    }

    private fun registerNewUser() {
        FirestoreOperations().addUserToFirsStore()
    }
}
        ********/