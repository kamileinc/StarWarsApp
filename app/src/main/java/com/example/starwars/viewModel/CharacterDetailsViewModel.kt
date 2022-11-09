package com.example.starwars.viewModel

import androidx.lifecycle.*
import com.example.starwars.R
import com.example.starwars.data.MainRepository
import com.example.starwars.data.entity.*
import com.example.starwars.utilities.Constants.PARAM_CHARACTER
import com.example.starwars.utilities.Resource
import com.example.starwars.utilities.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedCharacter = MutableLiveData<Character>()
    val selectedCharacter: LiveData<Character>
        get() = _selectedCharacter

    private val _films = MutableLiveData<Resource<List<Film>>>(Resource.Empty())
    val films: LiveData<Resource<List<Film>>>
        get() = _films

    private val filmsList: ArrayList<Film> = ArrayList()

    init {
        savedStateHandle.get<Character>(PARAM_CHARACTER)?.let { character ->
            _selectedCharacter.value = character
            getFilms(character.films)
        }
    }

    private fun getFilms(films: List<String>?) {
        viewModelScope.launch(Dispatchers.IO) {
            films?.forEach { url ->
                try {
                    _films.postValue(Resource.Loading())

                    val resource = mainRepository.getFilm(url)
                    resource.data?.let {
                        filmsList.add(it)
                        _films.postValue(Resource.Success(filmsList))
                    }
                } catch (e: HttpException) {
                    _films.postValue(Resource.Failure(
                        UiText.StringResource(resId = R.string.error)))
                } catch (e: IOException) {
                    _films.postValue(Resource.Failure(
                        UiText.StringResource(resId = R.string.internet_connection_failed)))
                }
            }
        }
    }
}
