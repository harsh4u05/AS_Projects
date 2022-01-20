package com.example.projectmodules01.ui.images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.projectmodules01.databinding.ImageItemBinding
import com.example.repositorymodule.entity.entities.Images
import com.example.repositorymodule.ui.GlideApp

//Interface ImageItemClickListener's onImageItemClick() will be implemented in ImagesFragment.
class ImagesAdapter(private val listener: ImageItemClickListener): RecyclerView.Adapter<ImageViewHolder>() {

    interface ImageItemClickListener {
        fun onImageItemClick(imageId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}

//ViewHolder must be passed a View for single adapter item: imageItem
//this viewHolder is a listener of clicks on its items, so implement View.OnClickListener for creating listener & listening to item clicks. onClick() will be called.
//set this viewHolder as listener to imagesBinding ViewGroup
class ImageViewHolder(private val imageBinding: ImageItemBinding, private val listener: ImagesAdapter.ImageItemClickListener):
    RecyclerView.ViewHolder(imageBinding.root), View.OnClickListener {

    private lateinit var image: Images

    init {
        imageBinding.root.setOnClickListener(this)
    }

    fun bind(item: Images) {
        this.image = item
        imageBinding.name.text = image.imageName

        GlideApp.with(imageBinding.root)
            .load(item.imageName)
            .transform(CircleCrop())
            .into(imageBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onImageItemClick(image.imageId)
    }

}