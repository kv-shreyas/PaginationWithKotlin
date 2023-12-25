package com.example.pagingwithkotlin

import androidx.paging.PagingSource
import androidx.paging.PagingState

class FilterAndSortPagingSource(
    private val items: List<Item>
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val currentPage = params.key ?: 0
            val filteredAndSortedItems = filterAndSortItems(items)

            val prevKey = if (currentPage > 0) currentPage - 1 else null
            val nextKey = if ((currentPage + 1) * params.loadSize < filteredAndSortedItems.size) {
                currentPage + 1
            } else {
                null
            }

            val pageItems = filteredAndSortedItems.subList(
                currentPage * params.loadSize,
                minOf((currentPage + 1) * params.loadSize, filteredAndSortedItems.size)
            )

            LoadResult.Page(
                data = pageItems,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun filterAndSortItems(items: List<Item>): List<Item> {
        // Filter items with even IDs
        val filteredItems = items.filter { it.id % 2 == 0L }

        // Sort items in descending order based on ID
        return filteredItems.sortedByDescending { it.id }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
