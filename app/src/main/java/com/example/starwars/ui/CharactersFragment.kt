package com.example.starwars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.starwars.CharacterPagerAdapter
import com.example.starwars.data.entity.Character
import com.example.starwars.databinding.FragmentCharactersBinding
import com.example.starwars.utilities.Constants.NOT_FOUND
import com.example.starwars.utilities.hideKeyboard
import com.example.starwars.viewModel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()
    private val adapter = CharacterPagerAdapter(CharacterPagerAdapter.OnClickListener {character -> onItemClick(character)})
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpAdapter()

        fetchCharacters("")

        binding.searchView.setEndIconOnClickListener {
            fetchCharacters(binding.searchView.editText!!.text.toString())
            hideKeyboard()
        }

        binding.searchCharacter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                fetchCharacters(binding.searchView.editText!!.text.toString())
                hideKeyboard()
            }
            true
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        fetchCharacters(binding.searchView.editText!!.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(character: Character) {
        val action =
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(
                character
            )
        findNavController().navigate(action)
    }

    private fun setUpAdapter() {
        binding.recyclerview.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading) {
                binding.noResults.isVisible = false
                binding.progressDialog.isVisible = true
            }
            else {
                binding.noResults.isVisible = false
                binding.progressDialog.isVisible = false
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    if (it.error.message?.contains(NOT_FOUND) == true && adapter.itemCount == 0) {
                            binding.noResults.isVisible = true
                    }
                }
            }
        }
    }

    private fun fetchCharacters(searchPhrase: String) {
        lifecycleScope.launch {
            viewModel.getCharacters(searchPhrase).collectLatest { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        }
    }
}
