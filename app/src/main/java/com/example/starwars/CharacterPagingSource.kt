package com.example.starwars

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.starwars.data.entity.Character
import com.example.starwars.data.entity.toCharacter
import com.example.starwars.data.entity.toCharacters
import com.example.starwars.data.network.RetrofitService
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val apiService: RetrofitService,
    private val searchPhrase: String
): PagingSource<Int, Character>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.getCharacters(nextPageNumber, searchPhrase)
            val characters = response.toCharacters().results.map { it.toCharacter() }

            LoadResult.Page(
                data = characters,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
