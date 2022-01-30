package com.example.projectmodules01.ui.images

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginmodule.login.LoginActivity
import com.example.projectmodules01.R
import com.example.projectmodules01.databinding.FragmentImagesBinding
import com.example.repositorymodule.entity.entities.Images
import com.example.repositorymodule.ui.MainActivity
import com.example.repositorymodule.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ImagesFragment : Fragment() {

    private lateinit var binding: FragmentImagesBinding
    private val viewModel: ImagesViewModel by viewModels()
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("----------- in onCreate()")

        if (FirebaseAuth.getInstance().currentUser == null) {
            Timber.d("-------------> User not authenticated. Going to Login.")
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("----------- in onCreateView()")
        binding = FragmentImagesBinding.inflate(inflater, container, false)
       // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("----------- in onViewCreated()")
        setupRecyclerView()
        binding.toImageButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
//            if(FirebaseAuth.getInstance().currentUser != null) {
//                FirebaseAuth.getInstance().signOut()
//            }
        }

        try{
            setupObservers()
        } catch (ex: Exception) {
            Timber.e("----------> Harsh Storage Exception : ${ex.localizedMessage}")
        }

    }

    override fun onResume() {
        super.onResume()
        Timber.d("----------- in onResume()")
        if (FirebaseAuth.getInstance().currentUser == null) {
            Timber.d("-------------> User not authenticated. Going to Login.")
            findNavController().navigate(R.id.loginFragment)
        }
    }
    private fun setupRecyclerView() {
        imagesAdapter = ImagesAdapter {
            adapterOnClick(it)
        }
        binding.imagesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.imagesRecyclerview.adapter = imagesAdapter
    }

    private fun setupObservers() {
        viewModel.imagesRefs.observe(requireActivity()) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                   // val images: List<Images>? = resource.data

                    imagesAdapter.submitList(resource.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun adapterOnClick(image: Images) {
        Timber.d("-----------> adapterOnClick")
        //Toast.makeText(requireContext(), "adapterOnClick method clicked", Toast.LENGTH_LONG).show()
    }

}