package com.example.testloginmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val userLiveData =  UserLiveData()

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