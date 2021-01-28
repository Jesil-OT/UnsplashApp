package com.jesil.example.mvvm.unsplashapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesil.example.mvvm.unsplashapp.databinding.UnsplashPhotoLoadStateFooterBinding


class UnsplashphotoLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<UnsplashphotoLoadStateAdapter.UnsplashPhotoLoadStateViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UnsplashPhotoLoadStateViewHolder {
        return UnsplashPhotoLoadStateViewHolder(
            UnsplashPhotoLoadStateFooterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UnsplashPhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState = loadState)
    }

   inner class UnsplashPhotoLoadStateViewHolder(
        private val binding: UnsplashPhotoLoadStateFooterBinding
        ) : RecyclerView.ViewHolder(binding.root){

            init {
                binding.buttonRetryError.setOnClickListener {
                    retry.invoke()
                }
            }

       fun bind(loadState : LoadState){
           binding.apply {
               progressBarFooter.isVisible = loadState is LoadState.Loading
               textViewError.isVisible = loadState !is LoadState.Loading
               buttonRetryError.isVisible = loadState !is LoadState.Loading
           }
       }
    }

}