package com.example.repositorymodule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.repositorymodule.R
import com.example.repositorymodule.databinding.RepositoryMainBinding
import com.example.repositorymodule.entity.firestorage.StorageDataSource
import com.example.repositorymodule.entity.firestorage.StorageMethods
import com.example.repositorymodule.entity.repository.ImagesRepository
import com.example.repositorymodule.utils.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var storageMethods: StorageMethods
    @Inject
    lateinit var storageDataSource: StorageDataSource
    @Inject
    lateinit var repository: ImagesRepository

    private val viewModel by viewModels<ImageViewModel>()

    private lateinit var binding: RepositoryMainBinding
    private val filePath = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = RepositoryMainBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.repository_main)
        setContentView(binding.root)
        filePath.add("Cancelled_Cheque.jpg")
        getGlideImageFromStorage()
    }

    private fun getGlideImageFromStorage() {

        viewModel.images.observe(this, Observer {

            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    Timber.d("---------- Main getGlideImageFromStorage() arrayList size : ${it.data!!.size}")
                    GlideApp.with(this)
                        //.load(Firebase.storage.reference.child("Cancelled_Cheque.jpg"))
                        .load(it.data!![0].child(filePath[0]))
                        .into(binding.imageView)
                }

                Resource.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }

        })
    }
}