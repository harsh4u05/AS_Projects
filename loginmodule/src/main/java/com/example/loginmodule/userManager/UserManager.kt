package com.example.loginmodule.userManager

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.loginmodule.login.LoginActivity
import com.example.loginmodule.storage.Storage
import com.example.utilitylibrary.FirebaseUtil
import com.example.utilitylibrary.FirestoreOperations
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

private const val REGISTERED_USER = "registered_user"
private const val PASSWORD_SUFFIX = "password"

@ActivityScoped
class UserManager @Inject constructor(@ApplicationContext val context: Context, private val storage: Storage) {

    //var userComponent: UserComponent = null
    //private set

    @Inject
    lateinit var firebaseUtil: FirebaseUtil

    companion object {
        private const val TAG = "------------ UserManager : "
    }

    var userLogged = false

    val username: String
    get() = firebaseUtil.getAuth().currentUser.toString() //storage.getString(REGISTERED_USER)

    //fun isUserLogged() = userComponent != null
    fun isUserRegistered() = storage.getString(REGISTERED_USER).isNotEmpty()

    fun registerUser(username: String, password: String) {
        storage.setString(REGISTERED_USER, username)
        storage.setString("$username$PASSWORD_SUFFIX", password)
        userJustLoggedIn()
    }

    private fun registerNewUser() {
        FirestoreOperations().addUserToFirsStore()
    }

    fun getUserLoginStatus(): Boolean {
        return firebaseUtil.getAuth().currentUser != null
    }

    fun unregister() {
        val username = storage.getString(REGISTERED_USER)
        storage.setString(REGISTERED_USER, "")
        storage.setString("$username$PASSWORD_SUFFIX", "")
        logout()
    }

    fun logout(){
        userLogged = false
        Log.e("----------- Harsh $TAG : ", "User to be Logged out is : ${firebaseUtil.getAuth().currentUser} ")
        firebaseUtil.getAuthUI().signOut(context)
            .addOnSuccessListener {
                Log.e("----------- Harsh $TAG : ", "User is Logged out : ${firebaseUtil.getAuth().currentUser} ")
            }
    }

    fun userJustLoggedIn() {
        Log.d(TAG, "Inside Usermanager userjustloggedin()")
        userLogged = true
    }

}