package com.example.projectmodules01.ui.images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.projectmodules01.databinding.ImageItemBinding
import com.example.repositorymodule.entity.entities.Images
import com.example.repositorymodule.ui.GlideApp
import com.example.repositorymodule.utils.getRefFromString
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import timber.log.Timber

//Interface ImageItemClickListener's onImageItemClick() will be implemented in ImagesFragment.
//class ImagesAdapter(private val listener: ImageItemClickListener): RecyclerView.Adapter<ImageViewHolder>() {
//class ImagesAdapter(private val listener: ImageItemClickListener):
//    ListAdapter<Images, ImageViewHolder>(ImageDiffCallback) {
class ImagesAdapter(private val onClick: (Images) -> Unit):
    ListAdapter<Images, ImageViewHolder>(ImageDiffCallback) {

    interface ImageItemClickListener {
        fun onImageItemClick(imageId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

//    override fun getItemCount(): Int {
//
//    }

}

//ViewHolder must be passed a View for single adapter item: imageItem
//this viewHolder is a listener of clicks on its items, so implement View.OnClickListener for creating listener & listening to item clicks. onClick() will be called.
//set this viewHolder as listener to imagesBinding ViewGroup
class ImageViewHolder(private val imageBinding: ImageItemBinding, private val onClick: (Images) -> Unit):
    RecyclerView.ViewHolder(imageBinding.root) {

    private var image: Images? = null

    init {
        imageBinding.root.setOnClickListener {
            image?.let {
                onClick(it)
            }
        }
    }

    fun bind(item: Images) {
        image = item
        val ref = getRefFromString(item.imageUri)
        Timber.d("------------>Image path : $ref")
       // val ref: StorageReference = item.imageRef.child("Cancelled_Cheque.jpg")

        imageBinding.name.text = ref.path
        try {
            GlideApp
                .with(imageBinding.root)
                //.load(Firebase.storage.reference.child("Cancelled_Cheque.jpg"))
                .load(ref)
                //.transform(CircleCrop())
                .override(100,200)
                .into(imageBinding.image)
        } catch (ex: Exception) {
            Timber.e("------------HarshGlide : Exception : ${ex.localizedMessage}")
        }

    }

}

object ImageDiffCallback: DiffUtil.ItemCallback<Images>() {
    override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
        return oldItem.imageId == newItem.imageId
    }

}