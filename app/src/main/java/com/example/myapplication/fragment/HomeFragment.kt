package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapter.HomeAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.helper.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main)
    private var hasInitializedRootView: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null)
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            val adapter = HomeAdapter()
            initializeRecylerView()
            binding.topsongs.adapter = adapter
            viewModel.getTopSongs().observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ResultWrapper.Loading -> binding.progressCircular.visibility = View.VISIBLE
                    is ResultWrapper.Error -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    }
                    is ResultWrapper.Success -> {
                        binding.progressCircular.visibility = View.GONE
                        println("Success load the data")
                        adapter.submitList(it.data)
                    }
                }
            })

            adapter.setItemClickListener {
                viewModel.clickedItem.postValue(it)
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailedFragment()
                )
            }

        }
    }

    private fun initializeRecylerView() {
        binding.topsongs.setHasFixedSize(true)

        binding.topsongs.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
        )

        binding.topsongs.addItemDecoration(dividerItemDecoration)

    }
}