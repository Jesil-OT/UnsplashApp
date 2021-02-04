package com.jesil.example.mvvm.unsplashapp.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.jesil.example.mvvm.unsplashapp.R
import com.jesil.example.mvvm.unsplashapp.adapter.UnsplashPagingAdapter
import com.jesil.example.mvvm.unsplashapp.adapter.UnsplashphotoLoadStateAdapter
import com.jesil.example.mvvm.unsplashapp.constant.Constants.TAG
import com.jesil.example.mvvm.unsplashapp.constant.OnItemClickListener
import com.jesil.example.mvvm.unsplashapp.data.UnsplashPhoto
import com.jesil.example.mvvm.unsplashapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val unsplashPagingAdapter = UnsplashPagingAdapter(this)
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
            btRetry.setOnClickListener {
                unsplashPagingAdapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner){result->
            unsplashPagingAdapter.submitData(viewLifecycleOwner.lifecycle,result)
            Log.d(TAG, "onViewCreated: $result")
        }

        unsplashPagingAdapter.addLoadStateListener { loadState ->

            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error


                // empty textView
                if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        unsplashPagingAdapter.itemCount < 1){
                    recyclerView.isVisible = false
                    tvEmpty.isVisible  = true
                }else{
                    tvEmpty.isVisible = false
                }
            }
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


    override fun onItemClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}