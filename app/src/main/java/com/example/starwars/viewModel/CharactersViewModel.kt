package com.example.starwars.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.starwars.data.MainRepository
import com.example.starwars.data.entity.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val mainRepository: MainRepository
    ) : ViewModel() {

    fun getCharacters(searchPhrase: String): Flow<PagingData<Character>> =
        mainRepository.getCharacters(searchPhrase).cachedIn(viewModelScope)
    }
