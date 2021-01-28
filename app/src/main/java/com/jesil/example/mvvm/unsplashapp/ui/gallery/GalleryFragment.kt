package com.jesil.example.mvvm.unsplashapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jesil.example.mvvm.unsplashapp.R
import com.jesil.example.mvvm.unsplashapp.adapter.UnsplashPagingAdapter
import com.jesil.example.mvvm.unsplashapp.adapter.UnsplashphotoLoadStateAdapter
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
                adapter = unsplashPagingAdapter.withLoadStateHeaderAndFooter(
                    header = UnsplashphotoLoadStateAdapter{
                          unsplashPagingAdapter.retry()
                    },
                    footer = UnsplashphotoLoadStateAdapter{
                        unsplashPagingAdapter.retry()
                    }
                )
            }
        }

        viewModel.photos.observe(viewLifecycleOwner){result->
            unsplashPagingAdapter.submitData(viewLifecycleOwner.lifecycle,result)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}