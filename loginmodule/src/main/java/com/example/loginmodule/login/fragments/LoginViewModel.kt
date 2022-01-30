package com.example.loginmodule.login.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val userLiveData =  UserLiveData()
    private val _userLogged = MutableLiveData<Boolean>()

    val userLogged: LiveData<Boolean> = _userLogged

    val authState = userLiveData.map { user ->
        if (user != null) {
            LoginViewState.LoginSuccess
        } else {
            LoginViewState.LoginError
        }

    }

}

sealed class LoginViewState {
    object LoginSuccess: LoginViewState()
    object LoginError: LoginViewState()
}
