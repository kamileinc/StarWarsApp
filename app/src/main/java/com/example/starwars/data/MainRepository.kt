package com.example.starwars.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.starwars.CharacterPagingSource
import com.example.starwars.data.entity.Character
import com.example.starwars.data.entity.Film
import com.example.starwars.data.entity.toFilm
import com.example.starwars.data.network.RetrofitService
import com.example.starwars.data.network.SafeApiCall
import com.example.starwars.utilities.Constants.NETWORK_PAGE_SIZE
import com.example.starwars.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val retrofitService: RetrofitService
    ): SafeApiCall() {

    fun getCharacters(searchPhrase: String): Flow<PagingData<Character>> {

        return  Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CharacterPagingSource(retrofitService, searchPhrase)
            },
            initialKey = 1
        ).flow
    }

    suspend fun getFilm(url: String): Resource<Film> = safeApiCall {
        retrofitService.getFilm(url).toFilm()
    }
}
