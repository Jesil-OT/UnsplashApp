package com.jesil.example.mvvm.unsplashapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jesil.example.mvvm.unsplashapp.R
import com.jesil.example.mvvm.unsplashapp.adapter.UnsplashPagingAdapter
import com.jesil.example.mvvm.unsplashapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val unsplashPagingAdapter = UnsplashPagingAdapter()
        binding.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = unsplashPagingAdapter
            }
        }

        viewModel.photos.observe(viewLifecycleOwner){result->
            unsplashPagingAdapter.submitData(viewLifecycleOwner.lifecycle,result)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}