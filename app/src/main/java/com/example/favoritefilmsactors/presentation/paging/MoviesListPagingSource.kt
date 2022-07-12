package com.example.favoritefilmsactors.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.SearchMovieParentUseCase
import javax.inject.Inject

class MoviesListPagingSource @Inject constructor(
    private val getMovies: SearchMovieParentUseCase,
    private val queryString: String,
    ) : PagingSource<Int, MovieSimple>() {

    var tempQuery = queryString

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSimple> {
        val pageIndex = params.key ?: 1
        val previousPageIndex = if (pageIndex == 1) null else pageIndex - 1

        val resultOfLoad = getMovies.execute(query = tempQuery, pageIndex = pageIndex)
        resultOfLoad.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = resultOfLoad.data!!,
            prevKey = previousPageIndex,
            nextKey = pageIndex + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieSimple>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}