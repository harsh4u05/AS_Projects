package com.example.utilitylibrary

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreOperations {

    @Inject
    lateinit var firebaseUtil: FirebaseUtil

    private val firebaseUser: FirebaseUser? =
        firebaseUtil.getAuth().currentUser!!

    private val firebaseFirestore: FirebaseFirestore = firebaseUtil.getFirestore()

    companion object {
        const val TAG = "utilitylibrary.FirestoreOperations"
    }

    fun addUserToFirsStore() {
        val newUser = User(firebaseUser!!.displayName, firebaseUser!!.uid)
        Log.i(TAG," --------> New user added to FireStore :  ${newUser.uID} :: ${newUser.name}")
        firebaseFirestore.collection("tmpUsers").document(firebaseUser.uid).set(newUser)
    }
}