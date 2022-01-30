package com.example.projectmodules01.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.projectmodules01.R
import com.example.projectmodules01.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toImages.setOnClickListener {
           // findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
            findNavController().navigate(R.id.action_mainFragment_to_imagesFragment)
        }
        binding.toProfile.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
        }
    }

    private fun goToProfileOrLogin() {
        if (FirebaseAuth.getInstance().currentUser != null ) {
            Timber.d("-------------Harsh 1: MainFragment User NOT null")
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
        } else {
            Timber.d("-------------Harsh 1: MainFragment User is NULL")
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }

}