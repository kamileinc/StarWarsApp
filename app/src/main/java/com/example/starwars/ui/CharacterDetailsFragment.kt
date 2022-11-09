package com.example.starwars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.starwars.FilmPagerAdapter
import com.example.starwars.databinding.FragmentCharacterDetailsBinding
import com.example.starwars.utilities.Resource
import com.example.starwars.viewModel.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val adapter = FilmPagerAdapter()
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpCharacterData()

        setUpAdapter()

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpCharacterData() {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
                binding.name.text = character.name
                binding.height.text = character.height
                binding.gender.text = character.gender
            }
        }
    }

    private fun setUpAdapter() {
        binding.recyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.films.observe(viewLifecycleOwner) { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.progressDialog.isVisible = false
                        adapter.submitList(event.data)
                        binding.recyclerview.adapter = adapter
                    }
                    is Resource.Failure -> {
                        binding.progressDialog.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            event.message?.asString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.progressDialog.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}
