package com.example.utilitylibrary

import android.content.Context
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUtil @Inject constructor() {

    private var firebaseFirestore: FirebaseFirestore = Firebase.firestore
    private var storage: FirebaseStorage = Firebase.storage
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var auth_UI: AuthUI = AuthUI.getInstance()
    private var sUseEmulators: Boolean = true

    fun setEmulators() {

        if (sUseEmulators) {
            firebaseFirestore!!.useEmulator("10.0.2.2", 8080)
            storage!!.useEmulator("10.0.2.2", 9199)
            firebaseAuth!!.useEmulator("10.0.2.2", 9099)
            auth_UI!!.useEmulator("10.0.2.2", 9099)
        }
    }

    fun getFirestore(): FirebaseFirestore {
        return firebaseFirestore!!
    }

    fun getFireStorage(): FirebaseStorage {
        return storage!!
    }

    fun getAuth(): FirebaseAuth {
        return firebaseAuth!!
    }

    fun getAuthUI(): AuthUI {
        return auth_UI!!
    }

}